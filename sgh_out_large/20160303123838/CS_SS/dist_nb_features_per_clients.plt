set title 'dist_nb_features_per_clients (AVG = 110.05882352941177)'
set xlabel 'Size (in Features)'
set ylabel '# Clients'
set xrange [0:16]
plot "dist_nb_features_per_clients.dat" using 1 notitle with line

