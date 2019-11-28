# Introduction
The Expression Language (also refer to as the EL) provides an important mechanism for enabling the presentation layer(web pages) to communicate with the application logic (managed beans).
The EL is used by JavaServer Faces technology and JavaServer Pages(JSP) technology.

 # Syntax
## Immediate and Deferred Evaluation Syntax

## Value and Method Expressions

## Defining  A Tag Attribute type

## Literal Expressions

## Operators
in addition to the . and [] in Value and Method Expressions, the EL provides the following operators, which can be used in rvalue expressions only:
* Arlthmetic: +, - (binary), *, / and div, % and mod
* Logical: and, &&, or, ||, not, !
* Relational: ==, eq, !=,ne, <, lt, >, gt, <=, ge, >=, le, Comparisons can be made against other values or against Boolean, string, integer, or floating-point literals.
* Empty: the empty operats is a prefix operation that can be used to determine whether a value is null or empty
* Conditional: A ？ B : C. Evaluate B or C, depending on the result of the evaluation of A.

### The Precedence of operators
The precedence of operators highest to lowest, left to right is as follows:
* [] .
* ()(used to change the precedence of operators)
* -(unary) not ! empty
* * / div % mod
* + -（binary)
* < > <= >= lt gt le ge
* == != eq ne
* && and
* || or
* ? :
## Reserved Words
The following words are reserved for the El and should not be used as identifiers:
            
|and|or|not|eq|
 :-------: |   :-----:  | :-------: | :------: 
|ne|lt|gt|le|
|ge|true|false|null|
|instance of | empty|div|mod|
 # Example
the following table contains example EL expressions and the result of evaluating them.

| EL Expression  |  Result  |
| -------------- | -------  |
|```${1 > (4/2)}```|false
|```${4.0 >= 3}```|true
|```${100.0 == 100}```|true
|```${(10*10) ne 100}```|false
```${'a' < 'b'}```|true
```${'hip' gt 'hit'}```|false
```${4 > 3}```|true
```${1.2E4 + 1.4}```|12001.4
```${3 div 4}```|0.75
```${10 mod 4}```|2
```${!empty param.Add}```|False if the request parameter named Add is null or an empty string.
```${pageContext.request.contextPath}```|The context path.
```${sessionScope.cart.numberOfItems}```|The value of the numberOfItems property of the session-scoped attribute named cart.
```${param['mycom.productId']}```|The value of the request parameter named mycom.productId.
```${header["host"]}```|The host.
```${departments[deptName]}```|The value of the entry named deptName in the departments map.
```${requestScope['javax.servlet.forward.servlet_path']}```|The value of the request-scoped attribute named javax.servlet.forward.servlet_path.
Gets the value of the property lName from the customer bean during an initial request. Sets the value of lName during a postback.
```#{customer.calcTotal}```|The return value of the method calcTotal of the customer bean


 # References
 [Java EE: 6. Expression Language ](https://docs.oracle.com/javaee/6/tutorial/doc/bnaim.html)
