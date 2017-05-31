set title 'dist_nb_features_per_servers (AVG = 29.41176470588235)'
set xlabel 'Size (in Features)'
set ylabel '# Servers'
set xrange [0:16]
plot "dist_nb_features_per_servers.dat" using 1 notitle with line

