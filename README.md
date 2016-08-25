# Glide can be faster on GIF

![GIF Loading](https://www.youtube.com/watch?v=Qqg5n7W6xZI)

Fresco : 12s
Glide : 7s

# How could it be?

```
Glide.with(MainActivity.this)
        .load(URL)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .into(glide);
```

set this : `diskCacheStrategy(DiskCacheStrategy.SOURCE)`

# Why?

See this issue : https://github.com/bumptech/glide/issues/281

tl;dr
RESULT contains transformation of GIF' frames. 