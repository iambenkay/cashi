# Transaction fee service

This service provides an API for calculating the transaction fees of a given transaction type. it also stores the transaction 
fee and rate at the time of calculation so even if the fee is updated in the future it does not impact already calculated transactions. 
The data source stored by this service will always serve as a source of truth for fees applied to transactions.

## Supported transaction types
The following are the supported transaction types that this service can
compute a fee for:

| Type               | Rate    |
|:-------------------|:--------|
| Mobile Top Up      | 0.0015  |
| Flight Booking     | 0.04    |
| Domestic Transfer  | 0.025   |

## How to run?
1. Clone this repository
2. Download and run the `restate-server` binary.
3. Run the kotlin service using `./gradlew run`. It will start listening on port `9090`.
4. Open the restate admin UI on your browser by visiting `http://localhost:9070`
5. Register a new deployment on restate by providing the URL to the kotlin service: `http://localhost:9090`
6. You can interact with the running kotlin service using the restate admin UI or by calling the ingress endpoint: `http://localhost:8080`

## Running tests
There are Cucumber-based BDD tests configured to cover the functionality of the kotlin service, and you can find them in [src/test/resources/features](src/test/resources/features).
To run these tests you need to:
1. Have a working docker installation
2. Run `docker pull restatedev/restate:latest` to pull the restate image
3. Run `./gradlew test` to run tests.