set title 'dist_nb_links_per_clients (AVG = 2.5531914893617023)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:46]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

