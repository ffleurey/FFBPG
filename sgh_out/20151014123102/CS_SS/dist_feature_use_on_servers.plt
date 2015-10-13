set title 'dist_feature_use_on_servers (AVG = 19.333333333333332)'
set xlabel 'Feature'
set ylabel '# of usages in servers'
set xrange [0:20]
plot "dist_feature_use_on_servers.dat" using 1 notitle with line

