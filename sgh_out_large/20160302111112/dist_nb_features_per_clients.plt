set title 'dist_nb_features_per_clients (AVG = 151.88235294117646)'
set xlabel 'Size (in Features)'
set ylabel '# Clients'
set xrange [0:16]
plot "dist_nb_features_per_clients.dat" using 1 notitle with line

