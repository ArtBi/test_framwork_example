# Pet Management Test Cases

* 1 Create and Retrieve Pet
    * Step 1: Create a new pet with valid data
    * Step 2: Verify the pet was created successfully (status code 200)
    * Step 3: Retrieve the pet by ID
    * Step 4: Verify the retrieved pet has the correct ID, name, status, and category

* 2 Delete Pet
    * Step 1: Create a new pet with valid data
    * Step 2: Delete the pet using its ID
    * Step 3: Verify the pet was deleted successfully (status code 200)
    * Step 4: Attempt to retrieve the deleted pet
    * Step 5: Verify the pet no longer exists (status code 404)

* 3 Find Pets by Status
    * Step 1: Create pets with different statuses (available, pending, sold)
    * Step 2: Search for pets with status "available"
    * Step 3: Verify the response contains at least one pet
    * Step 4: Verify all returned pets have the status "available"

* 4 Update Pet Status
    * Step 1: Create a new pet with status "available"
    * Step 2: Update the pet's status to "sold"
    * Step 3: Verify the update was successful (status code 200)
    * Step 4: Retrieve the pet by ID
    * Step 5: Verify the pet's status is now "sold"
