# Pet Management Test Suite

This test suite covers the core functionality of the Pet Management API, including creating, retrieving, updating, and
deleting pets.

## Test Cases

* 1 Create and Retrieve Pet
    * Verifies that a pet can be created with valid data and then retrieved by ID
    * Priority: Critical
    * Test Case: [Create and Retrieve Pet](../cases/pet-management.t.md#1-create-and-retrieve-pet)

* 2 Delete Pet
    * Verifies that a pet can be deleted and is no longer accessible after deletion
    * Priority: Critical
    * Test Case: [Delete Pet](../cases/pet-management.t.md#2-delete-pet)

* 3 Find Pets by Status
    * Verifies that pets can be found by their status
    * Priority: Normal
    * Test Case: [Find Pets by Status](../cases/pet-management.t.md#3-find-pets-by-status)

* 4 Update Pet Status
    * Verifies that a pet's status can be updated
    * Priority: Normal
    * Test Case: [Update Pet Status](../cases/pet-management.t.md#4-update-pet-status)

## Test Execution Order

1. Create and Retrieve Pet
2. Update Pet Status
3. Find Pets by Status
4. Delete Pet

## Prerequisites

* The Pet API service is running and accessible
* Test data is available for creating pets
* The test environment is properly configured
