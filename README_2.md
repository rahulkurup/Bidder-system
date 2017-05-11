For every incoming request as described in [1], send out bid requests as described in [2] to a configurable number of bidders[5]. Responses from these bidders as described in [3] must be processed. The highest bidder wins, and payload is sent out as described in [4].

### [1] Incoming Requests

An incoming request is of the following format:

```
http://localhost:8080/[id]?[key=value,...]
```

### [2] Bid Requests

A bid request is a POST request with the following JSON body format:

```json
{
	“id”: $id,
	“attributes” : {
		“$key”: “$value”,
		…
	}
}
```

### [3] Bid Response

The response will contain details of the bid:

```json
{
	"id" : $id,
	"bid": bid,
	"content":"the string to deliver as a response"
}
```

### [4] Auction Response

The response for the auction must be the `content` property of the winning bid, with some tags that can be mentioned in the content.

For now, only `$price$` must be supported, denoting the final price of the bid.

### [5] Configuration

* bidders: a comma-separated list of URLs denoting bidder endpoints
