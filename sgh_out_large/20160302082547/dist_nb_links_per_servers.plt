set title 'dist_nb_links_per_servers (AVG = 0.2492462311557789)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:1989]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

