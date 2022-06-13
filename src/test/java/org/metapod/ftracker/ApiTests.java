package org.metapod.ftracker;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void shouldPersistFlights() throws Exception {
        var postFlightsRequest = post("/api/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FLIGHTS_JSON);

        mockMvc.perform(postFlightsRequest)
                .andExpect(status().isCreated());

        var getFlightsRequest = get("/api/flights");

        mockMvc.perform(getFlightsRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].flightId", containsInRelativeOrder(0, 1)))
                .andExpect(jsonPath("$[*].flightNumber", containsInRelativeOrder(8524, 3624)));
    }

    @Test
    @Order(2)
    public void shouldPersistCargos() throws Exception {
        var postCargosRequest = post("/api/cargos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CARGOS_JSON);

        mockMvc.perform(postCargosRequest)
                .andExpect(status().isCreated());

        var getCargosRequest = get("/api/cargos");

        mockMvc.perform(getCargosRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].flightId", containsInRelativeOrder(0, 1, 1)))
                .andExpect(jsonPath("$[*].baggage[*].id", containsInRelativeOrder(0, 1, 0, 1)))
                .andExpect(jsonPath("$[*].cargo[*].id", containsInRelativeOrder(0, 1, 0, 1, 0, 1)));
    }

    @Test
    public void shouldReturnBadRequestGivenCargoWithoutCorrespondingFlight() throws Exception {
        var postCargoRequest = post("/api/cargos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_FLIGHT_CARGO_JSON);

        mockMvc.perform(postCargoRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void shouldReturnValidFlightWeightResponse() throws Exception {
        var flightWeightRequest = post("/api/info/weight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FLIGHT_WEIGHT_REQUEST);

        mockMvc.perform(flightWeightRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightId").value(1))
                .andExpect(jsonPath("$.baggageWeight.kg").value(427))
                .andExpect(jsonPath("$.baggageWeight.lb").value(941))
                .andExpect(jsonPath("$.cargoWeight.kg").value(1743))
                .andExpect(jsonPath("$.cargoWeight.lb").value(3843));
    }

    @Test
    @Order(4)
    public void shouldReturnValidAirportTrafficSummaryResponse() throws Exception {
        var flightWeightRequest = post("/api/info/traffic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AIRPORT_TRAFFIC_SUMMARY_REQUEST);

        mockMvc.perform(flightWeightRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departingFlights").value(2))
                .andExpect(jsonPath("$.arrivingFlights").value(0))
                .andExpect(jsonPath("$.departingBaggagePieces").value(992))
                .andExpect(jsonPath("$.arrivingBaggagePieces").value(0));
    }

    private static final String FLIGHTS_JSON =
            """
            [
              {
                "flightId": 0,
                "flightNumber": 8524,
                "departureAirportIATACode": "ANC",
                "arrivalAirportIATACode": "PPX",
                "departureDate": "2017-08-23T04:30:38-02:00"
              },
              {
                "flightId": 1,
                "flightNumber": 3624,
                "departureAirportIATACode": "SEA",
                "arrivalAirportIATACode": "PPX",
                "departureDate": "2021-07-13T12:28:49-02:00"
              }
            ]""";

    private static final String CARGOS_JSON =
            """
            [
              {
                "flightId": 0,
                "baggage": [
                  {
                    "id": 0,
                    "weight": 554,
                    "weightUnit": "lb",
                    "pieces": 265
                  },
                  {
                    "id": 1,
                    "weight": 682,
                    "weightUnit": "lb",
                    "pieces": 110
                  }
                ],
                "cargo": [
                  {
                    "id": 0,
                    "weight": 93,
                    "weightUnit": "kg",
                    "pieces": 311
                  },
                  {
                    "id": 1,
                    "weight": 756,
                    "weightUnit": "lb",
                    "pieces": 798
                  }
                ]
              },
              {
                "flightId": 1,
                "baggage": [
                  {
                    "id": 0,
                    "weight": 920,
                    "weightUnit": "lb",
                    "pieces": 329
                  },
                  {
                    "id": 1,
                    "weight": 21,
                    "weightUnit": "lb",
                    "pieces": 288
                  }
                ],
                "cargo": [
                  {
                    "id": 0,
                    "weight": 919,
                    "weightUnit": "kg",
                    "pieces": 801
                  },
                  {
                    "id": 1,
                    "weight": 725,
                    "weightUnit": "lb",
                    "pieces": 359
                  }
                ]
              },
              {
                "flightId": 1,
                "baggage": [],
                "cargo": [
                  {
                    "id": 0,
                    "weight": 192,
                    "weightUnit": "lb",
                    "pieces": 864
                  },
                  {
                    "id": 1,
                    "weight": 408,
                    "weightUnit": "kg",
                    "pieces": 235
                  }
                ]
              }
            ]""";

    private static final String INVALID_FLIGHT_CARGO_JSON =
            """
            [
               {
                  "flightId": 123,
                  "baggage": [],
                  "cargo": []
               }
            ]""";

    private static final String FLIGHT_WEIGHT_REQUEST =
            """
            {
                "flightNumber": 3624,
                "date": "2021-07-13T12:28:49-02:00"
            }""";


    private static final String AIRPORT_TRAFFIC_SUMMARY_REQUEST =
            """
            {
                "iataAirportCode": "PPX",
                "date": "2021-07-13T13:28:49-02:00"
            }""";
}
