# Problem

Automate a manual voucher approval process which includes 8 signatures before payment can be released.

# Solution

Implement a simple voucher approval process!

It's Java 11!

# todo list

- [ ] Principal is again NULL! There is no security on endpoints!
- [ ] Create new realm in keycloak; add users; add roles; add groups; add permissions;
- [X] Print all filters through which a request passes through - DONE
- [ ] Auditing; store who did what and when!
- [ ] Revisions
- [ ] State Machine
- [ ] Criteria & Query
- [ ] Hateoas
- [X] Jooq
- [ ] Logging params just generically
- [ ] Immutable, DDD, Events & State Machine?
- [ ] How/where to raise events for async emails? 
- [ ] Propagate errors from state machine to the caller
- [ ] Swagger & KeyCloak integration so, that frontend developers can play with API!
- [ ] Extract authorities and put in current user > First need to define the authorities
- [ ] Deploy app & run integration tests against it in mock environment
- [ ] Create integration tests

# Errors

- https://gist.github.com/rasheedamir/fae622f8e4bf5bcaa3d498026ee3e709