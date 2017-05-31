set title 'dist_nb_links_per_servers (AVG = 0.2066115702479339)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:241]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

