<?xml version="1.0" encoding="UTF-8"?>
<tileset name="Terrain" tilewidth="16" tileheight="16" spacing="1" tilecount="900" columns="30">
 <image source="terrain/terrain_spritesheet.png" width="512" height="512"/>
 <terraintypes>
  <terrain name="Water" tile="120"/>
  <terrain name="Grass" tile="5"/>
  <terrain name="Dirt" tile="6"/>
  <terrain name="Sand" tile="8"/>
  <terrain name="Stone" tile="7"/>
 </terraintypes>
 <tile id="0" type="Water" terrain="0,0,0,0">
  <properties>
   <property name="breakable" type="bool" value="false"/>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="1" type="Water" terrain="0,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="2" type="Water" terrain="1,1,1,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="3" type="Water" terrain="1,1,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="4" type="Water" terrain="1,1,0,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="5" type="Grass" terrain="1,1,1,1" probability="0.5">
  <properties>
   <property name="fieldUnit" value="grass"/>
  </properties>
 </tile>
 <tile id="6" type="Stone" terrain="2,2,2,2"/>
 <tile id="7" type="Stone" terrain="4,4,4,4">
  <properties>
   <property name="fieldUnit" value="stone"/>
  </properties>
 </tile>
 <tile id="8" type="Stone" terrain="3,3,3,3">
  <properties>
   <property name="fieldUnit" value="barren_land"/>
  </properties>
 </tile>
 <tile id="9" type="Stone">
  <properties>
   <property name="fieldUnit" value="stone"/>
  </properties>
 </tile>
 <tile id="10" type="Stone"/>
 <tile id="11" type="Sand"/>
 <tile id="12" type="Sand"/>
 <tile id="13" type="Sand"/>
 <tile id="14" type="Sand"/>
 <tile id="15" type="Sand"/>
 <tile id="16" type="Dirt"/>
 <tile id="17" type="Dirt"/>
 <tile id="18" type="Dirt"/>
 <tile id="19" type="Dirt"/>
 <tile id="20" type="Dirt"/>
 <tile id="21" type="Grass"/>
 <tile id="22" type="Grass"/>
 <tile id="23" type="Grass"/>
 <tile id="24" type="Grass"/>
 <tile id="25" type="Grass"/>
 <tile id="30" type="Water" terrain="0,0,0,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="31" type="Water" terrain="0,0,1,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="32" type="Water" terrain="1,0,1,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="33" type="Water" terrain="0,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="34" type="Water" terrain="0,1,0,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="35" type="Grass" terrain="1,1,1,1" probability="0.5">
  <properties>
   <property name="fieldUnit" value="grass"/>
  </properties>
 </tile>
 <tile id="36" type="Stone" terrain="2,2,2,2"/>
 <tile id="37" type="Stone" terrain="4,4,4,4">
  <properties>
   <property name="fieldUnit" value="stone"/>
  </properties>
 </tile>
 <tile id="38" type="Stone" terrain="3,3,3,3">
  <properties>
   <property name="fieldUnit" value="barren_land"/>
  </properties>
 </tile>
 <tile id="39" type="Stone"/>
 <tile id="40" type="Stone"/>
 <tile id="41" type="Sand"/>
 <tile id="42" type="Sand"/>
 <tile id="43" type="Sand"/>
 <tile id="44" type="Sand"/>
 <tile id="45" type="Sand"/>
 <tile id="46" type="Dirt"/>
 <tile id="47" type="Dirt"/>
 <tile id="48" type="Dirt"/>
 <tile id="49" type="Dirt"/>
 <tile id="50" type="Dirt"/>
 <tile id="51" type="Grass"/>
 <tile id="52" type="Grass"/>
 <tile id="53" type="Grass"/>
 <tile id="54" type="Grass"/>
 <tile id="55" type="Grass"/>
 <tile id="60" type="Water" terrain="0,1,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="61" type="Water" terrain="1,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="62" type="Water" terrain="1,0,1,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="63" type="Water" terrain="0,0,1,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="64" type="Water" terrain="0,1,1,1">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="66" type="Stone"/>
 <tile id="67" type="Stone"/>
 <tile id="68" type="Stone"/>
 <tile id="69" type="Stone"/>
 <tile id="70" type="Stone"/>
 <tile id="71" type="Sand"/>
 <tile id="72" type="Sand"/>
 <tile id="73" type="Sand"/>
 <tile id="74" type="Sand"/>
 <tile id="75" type="Sand"/>
 <tile id="76" type="Dirt"/>
 <tile id="77" type="Dirt"/>
 <tile id="78" type="Dirt"/>
 <tile id="79" type="Dirt"/>
 <tile id="80" type="Dirt"/>
 <tile id="83" type="Grass"/>
 <tile id="84" type="Grass"/>
 <tile id="85" type="Grass"/>
 <tile id="90" type="Sand" probability="0.333">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="91" type="Sand" probability="0.333">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="92" type="Water" terrain="4,4,4,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="93" type="Water" terrain="4,4,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="94" type="Water" terrain="4,4,0,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="96" type="Stone"/>
 <tile id="97" type="Stone"/>
 <tile id="98" type="Stone"/>
 <tile id="99" type="Stone" probability="0.334"/>
 <tile id="100" type="Stone"/>
 <tile id="101" type="Sand"/>
 <tile id="102" type="Sand"/>
 <tile id="103" type="Sand"/>
 <tile id="104" type="Sand" probability="0.334"/>
 <tile id="105" type="Sand"/>
 <tile id="106" type="Dirt"/>
 <tile id="107" type="Dirt"/>
 <tile id="108" type="Dirt"/>
 <tile id="109" type="Dirt"/>
 <tile id="110" type="Dirt"/>
 <tile id="111" type="Grass"/>
 <tile id="112" type="Grass"/>
 <tile id="113" type="Grass"/>
 <tile id="114" type="Grass"/>
 <tile id="115" type="Grass"/>
 <tile id="120" type="Water" terrain="0,0,0,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="121" type="Water" terrain="0,0,4,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="122" type="Water" terrain="4,0,4,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="123" type="Water" terrain="0,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="124" type="Water" terrain="0,4,0,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="125" terrain="2,2,2,1"/>
 <tile id="126" type="Stone" terrain="2,2,1,2"/>
 <tile id="127" type="Stone" terrain="1,1,1,2"/>
 <tile id="128" type="Stone" terrain="1,1,2,2"/>
 <tile id="129" type="Stone" terrain="1,1,2,1"/>
 <tile id="130" type="Stone" terrain="3,3,3,1"/>
 <tile id="131" type="Sand" terrain="3,3,1,3"/>
 <tile id="132" type="Sand" terrain="1,1,1,3"/>
 <tile id="133" type="Sand" terrain="1,1,3,3"/>
 <tile id="134" type="Sand" terrain="1,1,3,1"/>
 <tile id="135" type="Sand"/>
 <tile id="136" type="Dirt"/>
 <tile id="137" type="Dirt"/>
 <tile id="138" type="Dirt"/>
 <tile id="139" type="Dirt"/>
 <tile id="140" type="Dirt"/>
 <tile id="141" type="Grass"/>
 <tile id="142" type="Grass"/>
 <tile id="143" type="Grass"/>
 <tile id="144" type="Grass"/>
 <tile id="145" type="Grass"/>
 <tile id="150" type="Water" terrain="0,4,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="151" type="Water" terrain="4,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="152" type="Water" terrain="4,0,4,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="153" type="Water" terrain="0,0,4,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="154" type="Water" terrain="0,4,4,4">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="155" terrain="2,1,2,2"/>
 <tile id="156" type="Stone" terrain="1,2,2,2"/>
 <tile id="157" type="Stone" terrain="1,2,1,2"/>
 <tile id="158" type="Stone" terrain="2,2,2,2"/>
 <tile id="159" type="Stone" terrain="2,1,2,1"/>
 <tile id="160" terrain="3,1,3,3"/>
 <tile id="161" type="Sand" terrain="1,3,3,3"/>
 <tile id="162" type="Sand" terrain="1,3,1,3"/>
 <tile id="163" type="Sand" terrain="3,3,3,3"/>
 <tile id="164" type="Sand" terrain="3,1,3,1"/>
 <tile id="166" type="Dirt"/>
 <tile id="167" type="Dirt"/>
 <tile id="168" type="Dirt"/>
 <tile id="169" type="Dirt"/>
 <tile id="173" type="Grass"/>
 <tile id="174" type="Grass"/>
 <tile id="175" type="Grass"/>
 <tile id="180" type="Stone" probability="0.333">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="181" type="Stone" probability="0.333">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="182" type="Water" terrain="3,3,3,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="183" type="Water" terrain="3,3,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="184" type="Water" terrain="3,3,0,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="186" type="Water"/>
 <tile id="187" type="Water" terrain="1,2,1,1"/>
 <tile id="188" type="Water" terrain="2,2,1,1"/>
 <tile id="189" type="Water" terrain="2,1,1,1"/>
 <tile id="190" type="Water"/>
 <tile id="192" terrain="1,3,1,1"/>
 <tile id="193" terrain="3,3,1,1"/>
 <tile id="194" terrain="3,1,1,1"/>
 <tile id="201" type="Grass"/>
 <tile id="202" type="Grass"/>
 <tile id="203" type="Grass"/>
 <tile id="204" type="Grass"/>
 <tile id="205" type="Grass"/>
 <tile id="210" type="Water" terrain="0,0,0,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="211" type="Water" terrain="0,0,3,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="212" type="Water" terrain="3,0,3,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="213" type="Water" terrain="0,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="214" type="Water" terrain="0,3,0,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="216" type="Water"/>
 <tile id="217" type="Water"/>
 <tile id="218" type="Water"/>
 <tile id="219" type="Water"/>
 <tile id="220" type="Water"/>
 <tile id="231" type="Grass"/>
 <tile id="232" type="Grass"/>
 <tile id="233" type="Grass"/>
 <tile id="234" type="Grass"/>
 <tile id="235" type="Grass"/>
 <tile id="240" type="Water" terrain="0,3,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="241" type="Water" terrain="3,0,0,0">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="242" type="Water" terrain="3,0,3,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="243" type="Water" terrain="0,0,3,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="244" type="Water" terrain="0,3,3,3">
  <properties>
   <property name="fieldUnit" value="water"/>
  </properties>
 </tile>
 <tile id="248" type="Water"/>
 <tile id="249" type="Water"/>
 <tile id="250" type="Water"/>
 <tile id="263" type="Grass"/>
 <tile id="264" type="Grass"/>
 <tile id="265" type="Grass"/>
 <tile id="283" probability="0.333"/>
</tileset>
