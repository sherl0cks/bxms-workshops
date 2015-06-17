[condition][]Driver is at least {age} years old and lives in "{city}"=Driver(age >= {age}, city=="{city}")
[condition][]Driver is less than {age} years old=Driver(age < {age})
[condition][]Driver is greater than {age} years old=Driver(age > {age})
[condition][]Driver is between {lower} and {upper} years old=Driver(age >= {lower}, age <= {upper})
[consequence][]Logically insert a class Premium with the amount field set to {value}=Premium premium=new Premium();premium.setAmount({value});insertLogical(premium);
