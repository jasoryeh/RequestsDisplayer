# RequestsDisplayer [WIP]
Well in reality, I created this because Fiddler didn't work out for me.

### Avicus Network API
In addition this was originally intended to sniff out Magma's GraphQL requests because their Website was incomplete.

But, you know I'm gonna do the API anyways.

Gl hf. Still WIP, help me out if you can.

### Extra
GraphQL Responses: Basically json responses, but sorted into errors and data
```
{
    "data": {
        some array of data.
    },
    "errors": [
        "not sure, but maybe this is one error",
        "and this is another?"
    ]
}
```

GraphQL Queries: Not sure, but I got this one, so it's the example, similar to json, but is like a fill in the blank.
```
{
    allAfterAlert(created_at:"2018-12-17T18:28:02.132-08:00") {
        url,
        created_at,
        user_id,
        seen,
        id,
        message
    }
}
```

Take a look at this for GraphQL queries, and responses
```
net.avicus.magma.api.graph.mutations.MutationResponses
```

And for json stuff in each folder is a java file without "query" in the name it tells us what to look for in the constructor
```
net.avicus.magma.api.graph.types
```
