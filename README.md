# auto-completion-using-trie-dataStructure 

Theme:

Demonstration of searching  a word from a given text file using the trie data structure, implemented in java.

Explaination:

Given a word in the text box, we get all the words having the same prefix.
The crux of the idea lies in using the trie data structure for the search activity.
The trie data structure optimises the search  process.All the time complexities ,including best case,average case and worst case ,would be equal to T(the number of letters in the search string).This procedure would be very efficient in case of huge data sets and when we have search keys of varying length.
Alternatively,we could have done it using binary search trees or hashing.
But,binary search trees would have a higher complexity of O(nlogn).
The technique of hashing would not give the result in O(1) in case of having large numberof  strings having the same prefixes.
Instead ,the trie data structure simplifies the search to a large extent as it proceeds by identifying the prefix strings first.

Implementation of a trie node:

A trie node essentially consists of two fields.
The latter being a boolean value  "isEnd" that tells if that particular node results in the end of the string.
The former can be either a map that stores the character values or an array of trie nodes that store the characters.
In our application we have used an array of trie nodes as the first field as we have restricted our strings to having only lower case letters. 

As an additional note,these trie data structures can be used in building social networking sites, routing IP addresses etc...


