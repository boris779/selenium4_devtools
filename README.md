# selenium4_devtools Docker

started with the following commands:

from the resources directory:
`docker-compose -f docker-compose-dynamic-grid up`



If using ubuntu be sure that you changed your docker startup:
`/usr/lib/systemd/system/docker.service`

```
ExecStart=/usr/bin/dockerd -H fd:// -H=tcp://0.0.0.0:2375 --containerd=/run/containerd/containerd.sock
```

Attention there is a blank between -H and fd:// and a euqalsign (=) between -H and tcp://





