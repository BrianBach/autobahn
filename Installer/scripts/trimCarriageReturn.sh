#!/bin/bash
cat create_db.sh | tr -d '\r' > create_db.sh.bak
cat change-tunnel.sh | tr -d '\r' > change-tunnel.sh.bak
cat del-tunnels.sh | tr -d '\r' > del-tunnels.sh.bak
cat "do-install.sh" | tr -d '\r' > "do-install.sh.bak"
cat init_routing.sh | tr -d '\r' > init_routing.sh.bak
cat log.sh | tr -d '\r' > log.sh.bak
cat mark-tunnels.sh | tr -d '\r' > mark-tunnels.sh.bak
cat remove_from_conf.sh | tr -d '\r' > remove_from_conf.sh.bak
cat self-check.sh | tr -d '\r' > self-check.sh.bak
cat setupall.sh | tr -d '\r' > setupall.sh.bak
cat tunnels_installed_conf.sh | tr -d '\r' > tunnels_installed_conf.sh.bak
cat setup.sh | tr -d '\r' > setup.sh.bak
cat tunnels_installed.sh | tr -d '\r' > tunnels_installed.sh.bak
cat tunnels_not_installed.sh | tr -d '\r' > tunnels_not_installed.sh.bak
cat ../etc/idm.properties | tr -d '\r' > ../etc/idm.properties.bak
cat ../etc/dm.properties | tr -d '\r' > ../etc/dm.properties.bak
cat ../etc/calendar.properties | tr -d '\r' > ../etc/calendar.properties.bak
cat ../etc/ta.properties | tr -d '\r' > ../etc/ta.properties.bak
cat ../etc/public_ids.properties | tr -d '\r' > ../etc/public_ids.properties.bak
cat ../etc/services.properties | tr -d '\r' > ../etc/services.properties.bak
cat ../etc/framework.properties | tr -d '\r' > ../etc/framework.properties.bak
cat tunnels_not_installed.sh | tr -d '\r' > tunnels_not_installed.sh.bak
cp ../etc/idm.properties.bak ../etc/idm.properties
cp ../etc/dm.properties.bak ../etc/dm.properties
cp ../etc/calendar.properties.bak ../etc/calendar.properties
cp ../etc/ta.properties.bak ../etc/ta.properties
cp ../etc/public_ids.properties.bak ../etc/public_ids.properties
cp ../etc/services.properties.bak ../etc/services.properties
cp ../etc/framework.properties.bak ../etc/framework.properties
cp change-tunnel.sh.bak change-tunnel.sh
cp create_db.sh.bak create_db.sh
cp del-tunnels.sh.bak del-tunnels.sh
cp "do-install.sh.bak" "do-install.sh"
cp init_routing.sh.bak init_routing.sh
cp log.sh.bak log.sh
cp mark-tunnels.sh.bak mark-tunnels.sh
cp remove_from_conf.sh.bak remove_from_conf.sh
cp self-check.sh.bak self-check.sh
cp setupall.sh.bak setupall.sh
cp tunnels_installed_conf.sh.bak tunnels_installed_conf.sh
cp tunnels_installed.sh.bak tunnels_installed.sh
cp setup.sh.bak setup.sh
cp tunnels_not_installed.sh.bak tunnels_not_installed.sh
