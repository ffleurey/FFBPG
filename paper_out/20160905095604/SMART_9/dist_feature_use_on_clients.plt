set title 'dist_feature_use_on_clients (AVG = 28.666666666666668)'
set xlabel 'Feature'
set ylabel '# of usages in clients'
set xrange [0:14]
plot "dist_feature_use_on_clients.dat" using 1 notitle with line

