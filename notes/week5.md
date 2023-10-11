# Week5
## Useful command
`curl [-X POST/GET/PUT/DELETE] address [-H Content type] [-d Content]` to make request.  

## Notes
1. Search path: address + ReqeustMapping +  TypeMapping  

2. It is better to use wrapped type of primitive types otherwise it can not be compatible with null type.  

3. ResponseEntity should have matched types (e.g. you cannot have ResponseEntity<String> and ResponseEntity<Product> both returned). Adding response message into a response body seems harder than thinking.  
