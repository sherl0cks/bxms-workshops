[condition][]There is an Instance with field of "{value}"=i: Instance(field=="{value}")
[condition][]Instance is at least {number} and field is "{value}"=i: Instance(number > {number}, location=="{value}")
[consequence][]Log : "{message}"=System.out.println("{message}");
[consequence][]Set field of instance to "{value}"=i.setField("{value}");
[consequence][]Create instance : "{value}"=insert(new Instance("{value}"));
[condition][]There is no current Instance with field : "{value}"=not Instance(field == "{value}")
[consequence][]Report error : "{error}"=System.err.println("{error}");
[consequence][]Retract the fact : '{variable}'=retract({variable}); //this would retract bound variable {variable}
[*][String]=
[*][]=
