package view_tests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import domain.Agent;
import main.Application;
import repositories.AgentsRepository;

@SpringBootTest(classes ={Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentsDataControllerTest {

    @Autowired
    private WebApplicationContext context;

	//MockMvc --> Para realizar peticiones y comprobar resultado, usado para respuestas con informacion json.
	private MockMvc mockMvc;
	
	
	@Autowired
	private AgentsRepository repo;

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
	public void agentInsertInformation() throws Exception{
		String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}", 
									  maria.getUsername(), plainPassword, maria.getKind());
		
        //We send a POST request to that URI (from http:localhost...)
        MockHttpServletRequestBuilder request =
                post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.getBytes());

		assertEquals(mockMvc.perform(request).andReturn().getResponse().getStatus(), HttpStatus.OK.value());
        
		mockMvc.perform(request)
               .andDo(print())//AndDoPrint it is very usefull to see the http response and see if something went wrong.
			   .andExpect(jsonPath("$.name",is(maria.getName()))) //We can do jsonpaths in order to check that the json information displayes its ok.
               .andExpect(jsonPath("$.username", is(maria.getUsername())))
               .andExpect(jsonPath("$.email", is(maria.getEmail())))
               .andExpect(jsonPath("$.kind", is(maria.getKind())))
               .andExpect(jsonPath("$.kindName", is(maria.getKindName())));
	}
    
    @Test
    public void agentInsertInformationXML() throws Exception{
        String payload = String.format("<data><login>%s</login><password>%s</password><kind>%d</kind></data>",
        		maria.getUsername(), plainPassword, maria.getKind());
        
        //POST request with XML content
        MockHttpServletRequestBuilder request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_XML_VALUE).content(payload.getBytes());

		assertEquals(mockMvc.perform(request).andReturn().getResponse().getStatus(), HttpStatus.OK.value());
		
        mockMvc.perform(request)
                .andDo(print())//AndDoPrint it is very usefull to see the http response and see if something went wrong.
                .andExpect(jsonPath("$.name",is(maria.getName()))) //We can do jsonpaths in order to check that the json information displayes its ok.
                .andExpect(jsonPath("$.username", is(maria.getUsername())))
                .andExpect(jsonPath("$.email", is(maria.getEmail())))
                .andExpect(jsonPath("$.kind", is(maria.getKind())))
                .andExpect(jsonPath("$.kindName", is(maria.getKindName())));
    }
    
	@Test
	public void agentInterfaceInsertInfoCorect() throws Exception {
		MockHttpServletRequestBuilder request = post("/agentForm").session(session)
																 .param("login", maria.getUsername())
																 .param("password", plainPassword)
																 .param("kind", String.valueOf(maria.getKind()));
		
		assertEquals(mockMvc.perform(request)
						    .andReturn()
						    .getResponse()
						    .getStatus(), HttpStatus.OK.value());
	}

    @Test
    public void testForNotFound() throws Exception{
        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}", "Nothing", "Not really", 1111);
        MockHttpServletRequestBuilder request = post("/agent")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());
        
		assertEquals(mockMvc.perform(request)
			    				.andDo(print())
			    				.andReturn()
			    				.getResponse()
			    				.getStatus(), HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void testForIncorrectKind() throws Exception {
        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}",
				  maria.getUsername(), maria.getPassword(), maria.getKind() + 1);

		MockHttpServletRequestBuilder request = post("/agent")
				.session(session)
				.contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());

		assertEquals(mockMvc.perform(request)
			    			    .andDo(print())
			    			    .andReturn()
			    			    .getResponse()
			    			    .getStatus(), HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void testForIncorrectUsername() throws Exception {
        String payload = String.format("{\"login\":\"%s\", \"password\":\"%s\", \"kind\": \"%d\"}",
				  "Heung Min Son", maria.getPassword(), maria.getKind());

		MockHttpServletRequestBuilder request = post("/agent")
				.session(session)
				.contentType(MediaType.APPLICATION_JSON).content(payload.getBytes());
		
		assertEquals(mockMvc.perform(request)
						    .andDo(print())
						    .andReturn()
						    .getResponse()
						    .getStatus(), HttpStatus.NOT_FOUND.value());
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
        
		assertEquals(mockMvc.perform(request).andReturn().getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
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
		assertEquals(mockMvc.perform(request).andReturn().getResponse().getStatus(), HttpStatus.OK.value());

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
                .andExpect(jsonPath("$.username", is(maria.getUsername())))
                .andExpect(jsonPath("$.email", is(maria.getEmail())))
                .andExpect(jsonPath("$.kind", is(maria.getKind())))
                .andExpect(jsonPath("$.kindName", is(maria.getKindName())));
    }
    
}


