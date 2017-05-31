set title 'dist_nb_links_per_servers (AVG = 0.19364833462432224)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2581]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

