package view_tests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dbmanagement.UsersRepository;
import domain.Agent;
import main.Application;

@SpringBootTest(classes ={Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentsDataControllerTest {

    @Autowired
    private WebApplicationContext context;

	//MockMvc --> Para realizar peticiones y comprobar resultado, usado para respuestas con informacion json.
	private MockMvc mockMvc;
	
	
	@Autowired
	private UsersRepository repo;

	private MockHttpSession session;
	
	private Agent maria;
	private String plainPassword;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		session = new MockHttpSession();

		plainPassword = "pass14753";
		maria = new Agent("Maria", "maria@maria.es", plainPassword, "miUserName", 2);
		repo.insert(maria);
	}
	
	@After
	public void tearDown(){
		repo.delete(maria);
	}

    @Test
	public void userInsertInformation() throws Exception{
		String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}", 
									  maria.getUsername(), plainPassword, maria.getKind());
		
        //We send a POST request to that URI (from http:localhost...)
        MockHttpServletRequestBuilder request =
                post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.getBytes());
        
		mockMvc.perform(request)
               .andDo(print())//AndDoPrint it is very usefull to see the http response and see if something went wrong.
			   .andExpect(status().isOk()) //The state of the response must be OK. (200);
			   .andExpect(jsonPath("$.name",is(maria.getName()))) //We can do jsonpaths in order to check that the json information displayes its ok.
               .andExpect(jsonPath("$.identifier", is(maria.getUsername())))
               .andExpect(jsonPath("$.email", is(maria.getEmail())))
               .andExpect(jsonPath("$.kind", is(maria.getKind())));
	}
    
    @Test
    public void userInsertInformationXML() throws Exception{
        String payload = String.format("<data><login>%s</login><password>%s</password><kind>%d</kind></data>",
        		maria.getUsername(), plainPassword, maria.getKind());
        
        //POST request with XML content
        MockHttpServletRequestBuilder request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_XML_VALUE).content(payload.getBytes());
        
        mockMvc.perform(request)
                .andDo(print())//AndDoPrint it is very usefull to see the http response and see if something went wrong.
                .andExpect(status().isOk()) //The state of the response must be OK. (200);
                .andExpect(jsonPath("$.name",is(maria.getName()))) //We can do jsonpaths in order to check that the json information displayes its ok.
                .andExpect(jsonPath("$.identifier", is(maria.getUsername())))
                .andExpect(jsonPath("$.email", is(maria.getEmail())))
                .andExpect(jsonPath("$.kind", is(maria.getKind())));
    }
    
	@Test
	public void userInterfaceInsertInfoCorect() throws Exception {
		MockHttpServletRequestBuilder request = post("/agentForm")
				.session(session)
				.param("login", maria.getUsername())
				.param("password", plainPassword)
				.param("kind", String.valueOf(maria.getKind()));
		
		mockMvc.perform(request).andExpect(status().isOk());
	}

    @Test
    public void testForNotFound() throws Exception{
        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\"}", "Nothing", "Not really");
        MockHttpServletRequestBuilder request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());
        mockMvc.perform(request)
                .andDo(print())                    // AndDoPrint it is very usefull to see the http response and see if something went wrong.
                .andExpect(status().isNotFound()); // The state of the response must be OK. (200);
    }
    
    @Test
    public void testForIncorrectKind() throws Exception {
    	
    }
    
    @Test
    public void testForIncorrectUsername() throws Exception {
    	
    }

    /**
     * Should return a 404 as before
     */
    @Test
    public void testForIncorrectPassword() throws Exception {
        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}",
        								  maria.getUsername(), "Not maria's password", maria.getKind());
        
        MockHttpServletRequestBuilder request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testChangePassword() throws Exception {
        MockHttpSession session = new MockHttpSession();

        //We check we have the proper credentials
        MockHttpServletRequestBuilder request = post("/agentForm")
                .session(session)
                .param("login", maria.getUsername())
                .param("password", plainPassword)
                .param("kind", String.valueOf(maria.getKind()));
        mockMvc.perform(request).andExpect(status().isOk());
        
        //We change it
        request = post("/agentChangePassword")
                .session(session)
                .param("password", plainPassword)
                .param("newPassword", "HOLA")
                .param("newPasswordConfirm", "HOLA");
        mockMvc.perform(request).andExpect(status().isOk());

        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\":\"%d\"}",
        								  maria.getUsername(), "HOLA", maria.getKind());
        //We check password has changed
        request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());
        
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(maria.getName())))
                .andExpect(jsonPath("$.identifier", is(maria.getUsername())))
                .andExpect(jsonPath("$.email", is(maria.getEmail())))
                .andExpect(jsonPath("$.kind", is(maria.getKind())));
    }
    
}


