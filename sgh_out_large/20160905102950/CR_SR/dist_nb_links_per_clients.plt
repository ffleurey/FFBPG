set title 'dist_nb_links_per_clients (AVG = 2.976218787158145)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:840]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

