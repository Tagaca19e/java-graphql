### Java GraphQL

#### Queries

```graphql

# ----------------------------------------------------
# Filtering query.
# ----------------------------------------------------
query links {
  allLinks(filter:{description_contains:"back", url_contains:"cool"}) {
    description
    url
  }
}

# ----------------------------------------------------
# Pagination queries.
# ----------------------------------------------------
{
  allLinks(skip:1, first:1) {
    url
    description
  }
}

{
  allLinks(first: 2) {
    url
    description
  }
}

# ----------------------------------------------------
# Error handling queries.
# ----------------------------------------------------
query links {
  allLinks{
    description
    address
  }
}

mutation signin {
  signinUser(auth:{
    email:"bojack@example.com"
    password:"wrong"
  }) {
    token
  }
}

# ----------------------------------------------------
# Mutation queries, post in REST.
# ----------------------------------------------------
mutation createLink {
  createLink(url: "https://eidmonetagaca.com", description: "My website") {
    url
    description
  }
}

mutation {
  createUser (
    name: "Bojack Horseman"
    authProvider: {
      email: "bojack@example.com"
      password: "secret"
    }
  ) {
    id
    name
  }
}

mutation link {
  createLink(url:"https://en.wikipedia.org/wiki/Bojack_Horseman", description:"Bojack's Wiki entry") {
    url
  }
}

mutation vote {
  createVote(linkId: "6580ff26430c771859155056", userId:"6580e77bc48dc532b5db34af") {
    createdAt
		link {
      url
    }
    user {
      name
    }
  }
}

Sign in user payload.
mutation signIn {
  signinUser(auth:{email:"bojack@example.com", password:"secret"}) {
    token
    user {
      id
      name
    }
  }
}

# ----------------------------------------------------
# Query listing.
# ----------------------------------------------------

{
  allLinks {
    url
    description
  }
}

query links {
  allLinks {
    url
    description
  }
}

query all {
  allLinks {
    id
    url
    description
    postedBy {
      name
    }
  }
}

```