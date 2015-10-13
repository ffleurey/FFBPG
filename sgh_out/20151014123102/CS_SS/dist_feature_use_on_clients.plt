set title 'dist_feature_use_on_clients (AVG = 32.333333333333336)'
set xlabel 'Feature'
set ylabel '# of usages in clients'
set xrange [0:20]
plot "dist_feature_use_on_clients.dat" using 1 notitle with line

