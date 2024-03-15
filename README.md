# Power Asset Optimizer

A simple backend service for optimizing cost for electrical assets.
The service optimizes power usage for assets for the next day based on certain parameters.
The resolution for power usage changes is hourly. 

The main functionality can be summarized as follows:

- Registering assets
- Power usage optimization

## Registering assets

Register an asset through a POST request to the `/api/v1/asset` endpoint.

## Power usage optimization

Optimize power usage for a specific asset with a GET request to the `/api/v1/optimize/{assetName}` endpoint. The date to optimize for can be passed as a request parameter (optional, defaults to tomorrow).

The optimization will return a list of power usage for the next day, from midnight to midnight.
