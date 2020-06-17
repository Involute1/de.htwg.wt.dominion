package dominion

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class traurig extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7")
		.contentTypeHeader("application/x-www-form-urlencoded")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36")

	val headers_0 = Map(
		"Origin" -> "http://localhost:8080",
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "same-origin",
		"Sec-Fetch-User" -> "?1")



	val scn = scenario("traurig")
		.exec(http("request_0")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "local"))
		.pause(3)
		.exec(http("request_1")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "3"))
		.pause(2)
		.exec(http("request_2")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "a"))
		.pause(2)
		.exec(http("request_3")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "b"))
		.pause(2)
		.exec(http("request_4")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "c"))
		.pause(6)
		.exec(http("request_5")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "a"))
		.pause(3)
		.exec(http("request_6")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "1"))
		.pause(3)
		.exec(http("request_7")
			.post("/dominion")
			.headers(headers_0)
			.formParam("input", "a"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}