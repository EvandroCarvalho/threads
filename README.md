# threads
Brian Goetz in his famous book "[Java Concurrency in Practice](https://www.amazon.com.br/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601)" recommends the following formula:

```Ideal thread Count= Number of Cores * [ 1+ (wait time/CPU time)]```


**Waiting time** - is the time spent waiting for IO bound tasks to complete, say waiting for HTTP response from remote service.

(not only IO bound tasks, it could be time waiting to get monitor lock or time when thread is in WAITING/TIMED_WAITING state)

**Service time** - is the time spent being busy, say processing the HTTP response, marshaling/unmarshaling, any other transformations etc.

Wait time / Service time - this ratio is often called blocking coefficient.
