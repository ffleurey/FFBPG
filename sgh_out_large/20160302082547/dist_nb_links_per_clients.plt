set title 'dist_nb_links_per_clients (AVG = 4.012096774193548)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:495]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

