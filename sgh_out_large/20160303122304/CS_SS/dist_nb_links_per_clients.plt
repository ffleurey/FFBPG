set title 'dist_nb_links_per_clients (AVG = 4.52)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:499]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

