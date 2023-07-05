# Simulating Serverless Functions using CloudSim Plus

This repository can be used to simulate the deployment and operation of Serverless Functions in a global context.

This is done by defining an application and the desired countries for the functions to be deployed in.
Randomly generated users will try to request this application from different countries,
experiencing different latencies based on location.

Deployed functions require a startup time before requests can be fulfilled, have a predefined execution time
and will also shut down if their maximum idle time is reached.

## Setup

### Prerequisites

This project requires `Java 19` and `Maven` for Installation.

### Installation

Install the project dependencies using `mvn install`.

## Usage

Run the `main` method in the [Simulation](./src/main/java/me/jakobkraus/ocits/Simulation.java) class.
This will run the simulation using the static parameters in the class.

## Configuration

The static parameters in the [Simulation](./src/main/java/me/jakobkraus/ocits/Simulation.java) class can be configured
to simulate different behaviours.

The list of available countries can be configured using the
[Country](./src/main/java/me/jakobkraus/ocits/global/Country.java) enum.
When adding or removing a country the latency mapping
[CountryCostMapping](./src/main/java/me/jakobkraus/ocits/global/CountryCostMapping.java) has to be adjusted as well.

Description of static parameters:
- `SIMULATION_LENGTH`: the length of the simulation in seconds


- `HOSTS_PER_DATACENTER`: the number of hosts per datacenter
- `HOST_MIPS`: the number of instructions per host core in million instructions per second
- `HOST_RAM`: the amount of host ram in megabytes
- `HOST_BW`: the hosts bandwidth in megabytes
- `HOST_CORES`: the number of host cpu cores
- `HOST_STORAGE`: the amount of host storage in megabytes


- `VM_RAM`: the amount of vm ram in megabytes
- `VM_BANDWIDTH`: the vms bandwidth in megabytes
- `VM_SIZE`: the vms size in megabytes
- `VM_MIPS_CAPACITY`: the vms mips capacity in million instructions per second
- `VM_CORES`: the number of vm cpu cores


- `FUNCTION_STARTUP_TIME`: the startup time of a new function in seconds
- `FUNCTION_IDLE_TIME`: the maximum idle time of a function in seconds
- `FUNCTION_EXECUTION_TIME`: the time it takes for a function to execute a request
- `FUNCTION_SIZE`: the functions size in megabytes


- `NUMBER_OF_USERS`: the number of users to simulate
- `MINIMUM_USER_PERIOD`: the minimum period between requests for a user in seconds
- `MAXIMUM_USER_PERIOD`: the maximum period between requests for a user in seconds
- `MINIMUM_USER_MAX_REQUESTS`: the minimum number for the maximum amount of requests a user can send
- `MAXIMUM_USER_MAX_REQUESTS`: the maximum number for the maximum amount of requests a user can send


- `COUNTRIES_WITH_DATACENTER`: the countries with a datacenter
- `COUNTRIES_WITH_FUNCTION`: the countries where the application would like to deploy functions to. Does not have to overlap with `COUNTRIES_WITH_DATACENTER`
