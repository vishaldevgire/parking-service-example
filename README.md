# parking-service

Rest web service to book parking spots

Endpoints:

1. GET `localhost:8080/parking_spots/available`
   - Returns available parking spots
   
   ```
   [
       {
           "id": 5,
           "latitudes": 30,
           "longitude": 45,
           "height": 100,
           "width": 300
       }
   ]
   ```

2. GET `localhost:8080/parking_spots/reserved`
   - Returns reserved parking spots
   ```
   [
       {
           "id": 2,
           "latitudes": 30,
           "longitude": 45,
           "height": 100,
           "width": 300,
           "reservation": {
               "user": "Foo1",
               "start": "2019-06-13T07:53:53.867+0000",
               "end": "2019-07-06T07:53:53.864+0000",
               "cost": 690000
           }
       }
   ]
   ```
   
3. POST `localhost:8080/parking_spots/5/reserve`
   ```
   {"user": "Foo1", "numOfDays": 23}
   ```
   
   - Reserves a parking spot and returns reserved parking spot
   ```
   {
       "id": 5,
       "latitudes": 30,
       "longitude": 45,
       "height": 100,
       "width": 300,
       "reservation": {
           "user": "Foo1",
           "start": "2019-06-13T08:32:05.777+0000",
           "end": "2019-07-06T08:32:05.777+0000",
           "cost": 690000
       }
   }
   ```
   
4. POST `localhost:8080/parking_spots/5/cancel`
   - Cancels a reserved parking spot and returns cancelled parking spot
   
   ```
   {
       "id": 5,
       "latitudes": 30,
       "longitude": 45,
       "height": 100,
       "width": 300
   }
   ```
   
5. POST `localhost:8080/parking_spots/5/cost`
   ```
   {"user": "Foo1", "numOfDays": 23}
   ```
   - Returns cost of reserving a parking spot
   
   ```
   {
       "cost": 690000
   }
   ```
   
6. POST `localhost:8080/parking_spots/available/find?lat=30&lng=45&radius=1`
   - Returns available parking spots near given coordinates and radius in meters
   
   ```
   [
       {
           "id": 5,
           "latitudes": 30,
           "longitude": 45,
           "height": 100,
           "width": 300
       }
   ]
   ```