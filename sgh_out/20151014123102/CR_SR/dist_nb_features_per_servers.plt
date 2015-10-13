set title 'dist_nb_features_per_servers (AVG = 1.9583333333333333)'
set xlabel 'Size (in Features)'
set ylabel '# Servers'
set xrange [0:23]
plot "dist_nb_features_per_servers.dat" using 1 notitle with line

