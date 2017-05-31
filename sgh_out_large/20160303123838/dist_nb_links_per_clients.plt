set title 'dist_nb_links_per_clients (AVG = 3.77979797979798)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:494]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

