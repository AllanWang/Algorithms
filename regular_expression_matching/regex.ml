exception Invalid of string

let isMatch input regex =
  let trim s' = if String.length s' > 1 then String.sub s' 1 (String.length s' - 1) else "" in
  let isEmpty s' = String.length s' = 0 in
  let isCharMatch input' regex' = if regex' = '.' then true else input' = regex' in
  let rec isMatch' input' regex' cache' = if isEmpty input' then isEmpty regex' || regex' = "*"
    else if isEmpty regex' then isEmpty input'
    else
      let i = String.get input' 0 in
      let r = String.get regex' 0 in
      match r with
      | '*' ->
        (match cache' with
          | None -> raise (Invalid "Cannot start regex with '*'")
          | Some c -> if not (isCharMatch i c) then isMatch' input' (trim regex') cache'
            else isMatch' (trim input') (trim regex') cache' || isMatch' (trim input') regex' cache')
      | _ -> if not (isCharMatch i r) && (String.length regex' < 2 || String.get regex' 1 <> '*') then false
        else isMatch' (trim input') (trim regex') (Some r)
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
