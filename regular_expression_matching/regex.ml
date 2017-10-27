exception Invalid of string

let isMatch input regex =
  let debug = true in
  let trim s' = if String.length s' > 1 then String.sub s' 1 (String.length s' - 1) else "" in
  let isEmpty s' = String.length s' = 0 in
  let isCharMatch input' regex' = if regex' = '.' then true else input' = regex' in
  let rec isMatch' input' regex' cache' = if isEmpty input' then isEmpty regex' || regex' = "*"
    else if isEmpty regex' then isEmpty input'
    else
      (* print_string ("Input " ^ input' ^ "; Regex " ^ regex') *)
      let i = String.get input' 0 in
      let r = String.get regex' 0 in
      match r with
      | '*' ->
        (match cache' with
          | None -> raise (Invalid "Cannot start regex with '*'")
          | Some c -> if not (isCharMatch i c) then false
            else isMatch' (trim input') (trim regex') cache' || isMatch' (trim input') regex' cache') || isMatch' input' (trim regex') cache'
      | _ -> if not (isCharMatch i r) then false else isMatch' (trim input') (trim regex') (Some r)
  in
  isMatch' input regex None

(* Test cases *)

(* isMatch "aa" "a"
isMatch "aa" "aa"
isMatch "aaa""aa"
isMatch "aa" "a*"
isMatch "aa" ".*"
isMatch "ab" ".*"
isMatch "aab" "c*a*b" *)
