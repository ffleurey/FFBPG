# plot "RandomAllLinks_srv_dist.dat" using 2 notitle smooth frequency with boxes

binwidth=1

bin(x,width)=width*floor(x/width) + binwidth/2.0
plot "RandomAllLinks_srv_dist.dat" using (bin($52, binwidth)):1 smooth freq with line