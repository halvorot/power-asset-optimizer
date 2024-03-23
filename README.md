# Power Asset Optimizer

A simple backend service for optimizing cost for electrical assets.
The service optimizes power usage for assets for the next day based on certain parameters.
The resolution for power usage changes is hourly.

The main functionality can be summarized as follows:

- Registering assets
- Power usage optimization

## Running the application

Run the application by cloning the project or by using Docker:

```cmd
docker run -p 8080:8080 -d halvorot/power-asset-optimizer:latest
```

The application is now running on http://localhost:8080

## API

See the API documentation at `/swagger-ui`.

The OpenAPI specification can be found at `/v3/api-docs`.

### Registering assets

Register an asset through a POST request to the `/api/v1/asset` endpoint.

An asset has the following properties:

- Name (unique identifier)
- Minimum power usage (Kilowatts)
- Maximum power usage (Kilowatts)
- Total energy usage per 24 hours (Kilowatt hours)
- Price area (NO1-NO5)

### Power usage optimization

Optimize power usage for a specific asset with a GET request to the `/api/v1/optimize/{assetName}`
endpoint. The date to optimize for can be passed as a request parameter (optional, defaults to
tomorrow).

The optimization will return a list of power usage for the next day, from midnight to midnight.
