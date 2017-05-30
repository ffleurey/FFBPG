set title 'dist_feature_use_on_servers (AVG = 12.866666666666667)'
set xlabel 'Feature'
set ylabel '# of usages in servers'
set xrange [0:14]
plot "dist_feature_use_on_servers.dat" using 1 notitle with line

