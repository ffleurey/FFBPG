set title 'dist_feature_use_on_clients (AVG = 1031.4375)'
set xlabel 'Feature'
set ylabel '# of usages in clients'
set xrange [0:15]
plot "dist_feature_use_on_clients.dat" using 1 notitle with line

