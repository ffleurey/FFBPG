set title 'dist_nb_links_per_clients (AVG = 4.84)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:49]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

