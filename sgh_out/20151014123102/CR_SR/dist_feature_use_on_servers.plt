set title 'dist_feature_use_on_servers (AVG = 16.916666666666668)'
set xlabel 'Feature'
set ylabel '# of usages in servers'
set xrange [0:23]
plot "dist_feature_use_on_servers.dat" using 1 notitle with line

