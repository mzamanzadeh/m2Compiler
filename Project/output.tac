VAR  4,func1
FUN  func,4
ARG  4,a
ARG  4,b
+  $t1,a,b
RET  $t1
END  func
FUN  start,4
VAR  4,a
JNZ  Label0,#1
+  a,a,1
LB  Label0
ARR  4,b,1750
VAR  4,c
CALL  func
VAR  4,i,func
*  $t2,#1,#1000
+  $t3,@b,$t2
*  $t4,i,#100
+  $t5,$t3,$t4
*  $t6,i,#20
+  $t7,$t5,$t6
*  $t8,#4,#4
+  $t9,$t7,$t8
=  @$t9,#4
LB  Label1
=  $t10,#0
LB  Label1
+  $t11,@b,$t10
=  a,@$t11
VAR  4,forE,a
==  $t12,a,#2
JNZ  Label2,$t12
VAR  4,insideIF
JMP  Label3
LB  Label2
VAR  4,startSW
LB  Label5
-  $t12,startSW,1
JNZ  Label6,$t12
JMP  Label4
LB  Label6
-  $t12,startSW,2
JNZ  Label7,$t12
JMP  Label4
LB  Label7
VAR  4,default1
LB  Label4
VAR  4,afterSW
LB  Label3
+  $t10,$t10,4
>  $t14,$t10,7000
JZ  Label1,$t14
END  start
