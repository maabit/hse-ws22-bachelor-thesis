package de.doubleslash.tt293.vertxapp.restapi;

import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class AuthorControllerTest {

//  private MockWebServer mockWebServer;
//
//  @BeforeEach
//  public void setUp() {
//    mockWebServer = new MockWebServer();
//  }
//
//
//  @Test
//  public void test_getAllAuthors(final Vertx vertx, VertxTestContext testContext) throws InterruptedException {
//    // given
//    JsonArray jsonArray = new JsonArray();
//
//    AuthorDto authorDto1 = new AuthorDto(new JsonObject());
//    authorDto1.setId("1");
//    authorDto1.setName("Test Author 1");
//    AuthorDto authorDto2 = new AuthorDto();
//    authorDto2.setId("2");
//    authorDto2.setName("Test Author 2");
//
//    jsonArray.add(authorDto1.getJson());
//    jsonArray.add(authorDto2.getJson());
//
//    mockWebServer.enqueue(new MockResponse()
//      .setBody(jsonArray.toString())
//      .setResponseCode(200)
//      .setHeader("content-type", "application/json"));
//
//    // when
//    final String webServiceUrl = mockWebServer.url("/authors/").toString();
//
//
//    // then
//
//    final RecordedRequest recordedRequest = mockWebServer.takeRequest();
//    assertEquals("POST", recordedRequest.getMethod());
//    assertEquals("[text={\"request\":[]}]", recordedRequest.getBody().toString());
//    assertEquals(1, mockWebServer.getRequestCount());
//    testContext.assertComplete(stringPromise.future())
//      .map(val -> {
//        assertEquals("promise_completed", val);
//        testContext.completeNow();
//        return val;
//      })
//      .onComplete(onComplete -> {
//        assertTrue(onComplete.succeeded());
//      })
//      .onFailure(onError -> Assertions.fail());
//
//  }
}
