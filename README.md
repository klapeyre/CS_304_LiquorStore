# CS_304_LiquorStore

*Tracking changes from initial relations/formal specs
- dropped normalization of the reports table; no longer decomposed into two
- instances of having both time and date fields in a given table combined into one timestamp field
- reports table has date field, while all others have timestamps. This is because reports will search for values by date and include any timestamps that fall within a range
