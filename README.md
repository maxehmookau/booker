# Booking

Clojure REST API for the Box UK Hackfest 

## Usage

Setup

Make sure Postgres is running and create the database

```
createdb booking
```

Create the database tables

```
lein run -m booking.setup
```

### ROOMS

Get all meeting rooms

```
curl -i -X http://localhost:3000/rooms
```

Get information about a single meeting room by room ID

```
curl -i -X http://localhost:3000/rooms/1
```

Create a meeting room


## BOOKINGS

Get all bookings for a given date

```
curl -i -X http://localhost:3000/rooms/1/bookings?date="01-02-2013"
```

Get all bookings for a room

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
