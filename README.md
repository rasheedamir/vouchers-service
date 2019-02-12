# Problem

Automate a manual claimedVoucher approval process which includes 8 signatures before payment can be released.

# Solution

Implement a simple claimedVoucher approval process with Activiti 7

It's Java 11!

# todo list

- [ ] Create new realm in keycloak; add users; add roles; add groups; add permissions;
- [X] Link activiti security with spring security more globally
- [X] Configure app to use UserDetailsService with KeyCloak - by adding right dependencies it starts working
- [X] Print all filters through which a request passes through - DONE
- [ ] Auditing
- [ ] Revisions
- [ ] State Machine
- [ ] Criteria & Query
- [ ] Hateoas
- [X] jooq
- [ ] Logging params just generically
- [ ] Principal is again NULL!
- [ ] Immutable, DDD & State Machine?

# Errors

- https://gist.github.com/rasheedamir/fae622f8e4bf5bcaa3d498026ee3e709