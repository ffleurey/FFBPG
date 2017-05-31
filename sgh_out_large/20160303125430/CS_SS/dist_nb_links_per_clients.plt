set title 'dist_nb_links_per_clients (AVG = 3.8768920282542885)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:990]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

