set title 'dist_nb_features_per_servers (AVG = 1.8125)'
set xlabel 'Size (in Features)'
set ylabel '# Servers'
set xrange [0:15]
plot "dist_nb_features_per_servers.dat" using 1 notitle with line

