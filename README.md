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

```clojure
curl -i -X http://localhost:3000/rooms
```

Get information about a single meeting room by room ID

```clojure
curl -i -X http://localhost:3000/rooms/1
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
