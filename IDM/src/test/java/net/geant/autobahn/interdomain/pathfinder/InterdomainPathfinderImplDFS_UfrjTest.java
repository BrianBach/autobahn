/**
 * 
 */
package net.geant.autobahn.interdomain.pathfinder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImplDFS;
import net.geant.autobahn.interdomain.pathfinder.Topology;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.reservation.Reservation;

/**
 * @author kostas
 *
 */
public class InterdomainPathfinderImplDFS_UfrjTest {

    // Topology simulates the UFRJ supplied topology
    Topology topology_straight;
    AdminDomain cliente8_usp, 
        cliente2_cpqd, 
        cliente7_usp, 
        cliente1_cpqd, 
        cliente4_rnprj, 
        cliente3_rnprj, 
        cliente6_ufrj, 
        cliente5_ufrj, 
        UFPA, 
        cliente1, 
        cliente2, 
        cliente3, 
        cliente4, 
        cliente24, 
        cliente23, 
        cliente15, 
        cliente16, 
        cliente17, 
        cliente18, 
        cliente19, 
        cliente20, 
        cliente21, 
        cliente22, 
        cliente14, 
        cliente12, 
        cliente13, 
        cliente11,
        cliente10, 
        cliente9, 
        cliente8, 
        cliente7, 
        cliente6, 
        cliente5, 
        UFRJ;
    ProvisioningDomain pd_cliente8_usp, 
        pd_cliente2_cpqd, 
        pd_cliente7_usp, 
        pd_cliente1_cpqd, 
        pd_cliente4_rnprj, 
        pd_cliente3_rnprj, 
        pd_cliente6_ufrj, 
        pd_cliente5_ufrj, 
        pd_UFPA, 
        pd_cliente1, 
        pd_cliente2, 
        pd_cliente3, 
        pd_cliente4, 
        pd_cliente24, 
        pd_cliente23, 
        pd_cliente15, 
        pd_cliente16, 
        pd_cliente17, 
        pd_cliente18, 
        pd_cliente19, 
        pd_cliente20, 
        pd_cliente21, 
        pd_cliente22, 
        pd_cliente14, 
        pd_cliente12, 
        pd_cliente13, 
        pd_cliente11,
        pd_cliente10, 
        pd_cliente9, 
        pd_cliente8, 
        pd_cliente7, 
        pd_cliente6, 
        pd_cliente5, 
        pd_UFRJ;
    Node n10_20_0_12, 
        n10_20_0_13, 
        n10_20_0_3, 
        n10_20_0_14, 
        n10_20_0_15, 
        n10_20_0_0, 
        n10_20_0_1, 
        n10_20_0_6, 
        n10_20_0_7, 
        n10_20_0_4, 
        n10_20_0_5, 
        n10_20_0_10, 
        n10_20_0_11, 
        n10_20_0_8, 
        n10_20_0_9, 
        n10_10_0_1, 
        n10_10_0_2, 
        n10_10_0_3, 
        n10_10_0_4, 
        n10_10_0_5, 
        n10_10_0_6, 
        n10_10_0_33, 
        n10_10_0_35, 
        n10_10_0_34, 
        n10_10_0_22, 
        n10_10_0_23, 
        n10_10_0_24, 
        n10_10_0_25, 
        n10_10_0_26, 
        n10_10_0_27, 
        n10_10_0_28, 
        n10_10_0_29, 
        n10_10_0_30, 
        n10_10_0_0, 
        n10_10_0_31, 
        n10_10_0_32, 
        n10_10_0_19, 
        n10_10_0_21, 
        n10_10_0_16, 
        n10_10_0_18, 
        n10_10_0_20, 
        n10_10_0_17, 
        n10_10_0_13, 
        n10_10_0_15, 
        n10_10_0_14, 
        n10_10_0_10, 
        n10_10_0_12, 
        n10_10_0_11, 
        n10_10_0_7, 
        n10_10_0_9, 
        n10_10_0_8, 
        n10_20_0_2;
    Port p10_20_32_62, 
        p10_20_32_71, 
        p10_20_32_57, 
        p10_20_32_66, 
        p10_20_32_63, 
        p10_20_32_72, 
        p10_20_32_56, 
        p10_20_32_65, 
        p10_20_32_59, 
        p10_20_32_68, 
        p10_20_32_58, 
        p10_20_32_67, 
        p10_20_32_61, 
        p10_20_32_70, 
        p10_20_32_60, 
        p10_20_32_69, 
        p10_20_32_0, 
        p10_20_32_1, 
        p10_20_32_2, 
        p10_20_32_3, 
        p10_20_32_4, 
        p10_20_32_5, 
        p10_20_32_6, 
        p10_20_32_7, 
        p10_20_32_8, 
        p10_20_32_9, 
        p10_20_32_10, 
        p10_20_32_11, 
        p10_20_32_12, 
        p10_20_32_13, 
        p10_20_32_14, 
        p10_20_32_15, 
        p10_20_32_16, 
        p10_20_32_17, 
        p10_20_32_18, 
        p10_20_32_19, 
        p10_20_32_20, 
        p10_20_32_21, 
        p10_20_32_22, 
        p10_20_32_23, 
        p10_20_32_24, 
        p10_20_32_25, 
        p10_20_32_26, 
        p10_20_32_27, 
        p10_20_32_28, 
        p10_20_32_29, 
        p10_20_32_30, 
        p10_20_32_31, 
        p10_20_32_32, 
        p10_20_32_33, 
        p10_20_32_34, 
        p10_20_32_35, 
        p10_20_32_36, 
        p10_20_32_37, 
        p10_20_32_38, 
        p10_20_32_39, 
        p10_20_32_40, 
        p10_20_32_41, 
        p10_20_32_42, 
        p10_20_32_43, 
        p10_20_32_44, 
        p10_20_32_45, 
        p10_20_32_46, 
        p10_20_32_47, 
        p10_20_32_48, 
        p10_20_32_49, 
        p10_20_32_50, 
        p10_20_32_51, 
        p10_20_32_52, 
        p10_20_32_53, 
        p10_20_32_54, 
        p10_20_32_55, 
        p10_10_32_133, 
        p10_10_32_157, 
        p10_10_32_134, 
        p10_10_32_158, 
        p10_10_32_135, 
        p10_10_32_159, 
        p10_10_32_136, 
        p10_10_32_160, 
        p10_10_32_156, 
        p10_10_32_180, 
        p10_10_32_155, 
        p10_10_32_179, 
        p10_10_32_147, 
        p10_10_32_171, 
        p10_10_32_148, 
        p10_10_32_172, 
        p10_10_32_149, 
        p10_10_32_173, 
        p10_10_32_150, 
        p10_10_32_174, 
        p10_10_32_151, 
        p10_10_32_175, 
        p10_10_32_152, 
        p10_10_32_176, 
        p10_10_32_153, 
        p10_10_32_177, 
        p10_10_32_154, 
        p10_10_32_178, 
        p10_10_32_146, 
        p10_10_32_170, 
        p10_10_32_144, 
        p10_10_32_168, 
        p10_10_32_132, 
        p10_20_32_64, 
        p10_10_32_145, 
        p10_10_32_169, 
        p10_10_32_143, 
        p10_10_32_167, 
        p10_10_32_142, 
        p10_10_32_166, 
        p10_10_32_141, 
        p10_10_32_165, 
        p10_10_32_140, 
        p10_10_32_164, 
        p10_10_32_38, 
        p10_10_32_39, 
        p10_10_32_40, 
        p10_10_32_41, 
        p10_10_32_42, 
        p10_10_32_43, 
        p10_10_32_44, 
        p10_10_32_45, 
        p10_10_32_46, 
        p10_10_32_47, 
        p10_10_32_48, 
        p10_10_32_49, 
        p10_10_32_50, 
        p10_10_32_51, 
        p10_10_32_52, 
        p10_10_32_53, 
        p10_10_32_56, 
        p10_10_32_57, 
        p10_10_32_58, 
        p10_10_32_59, 
        p10_10_32_60, 
        p10_10_32_61, 
        p10_10_32_62, 
        p10_10_32_63, 
        p10_10_32_64, 
        p10_10_32_65, 
        p10_10_32_66, 
        p10_10_32_67, 
        p10_10_32_68, 
        p10_10_32_69, 
        p10_10_32_70, 
        p10_10_32_71, 
        p10_10_32_72, 
        p10_10_32_73, 
        p10_10_32_74, 
        p10_10_32_75, 
        p10_10_32_139, 
        p10_10_32_163, 
        p10_10_32_138, 
        p10_10_32_162, 
        p10_10_32_137, 
        p10_10_32_161, 
        p10_10_32_0, 
        p10_10_32_1, 
        p10_10_32_2, 
        p10_10_32_3, 
        p10_10_32_4, 
        p10_10_32_5, 
        p10_10_32_6, 
        p10_10_32_7, 
        p10_10_32_8, 
        p10_10_32_9, 
        p10_10_32_10, 
        p10_10_32_11, 
        p10_10_32_12, 
        p10_10_32_13, 
        p10_10_32_14, 
        p10_10_32_15, 
        p10_10_32_16, 
        p10_10_32_17, 
        p10_10_32_18, 
        p10_10_32_19, 
        p10_10_32_20, 
        p10_10_32_21, 
        p10_10_32_22, 
        p10_10_32_23, 
        p10_10_32_24, 
        p10_10_32_25, 
        p10_10_32_26, 
        p10_10_32_27, 
        p10_10_32_28, 
        p10_10_32_29, 
        p10_10_32_30, 
        p10_10_32_31, 
        p10_10_32_32, 
        p10_10_32_33, 
        p10_10_32_34, 
        p10_10_32_35, 
        p10_10_32_36, 
        p10_10_32_37, 
        p10_10_32_54, 
        p10_10_32_55, 
        p10_10_32_76, 
        p10_10_32_77, 
        p10_10_32_78, 
        p10_10_32_79, 
        p10_10_32_80, 
        p10_10_32_81, 
        p10_10_32_82, 
        p10_10_32_83, 
        p10_10_32_84, 
        p10_10_32_85, 
        p10_10_32_86, 
        p10_10_32_87, 
        p10_10_32_88, 
        p10_10_32_89, 
        p10_10_32_90, 
        p10_10_32_91, 
        p10_10_32_92, 
        p10_10_32_93, 
        p10_10_32_94, 
        p10_10_32_95, 
        p10_10_32_96, 
        p10_10_32_97, 
        p10_10_32_98, 
        p10_10_32_99, 
        p10_10_32_100, 
        p10_10_32_101, 
        p10_10_32_102, 
        p10_10_32_103, 
        p10_10_32_104, 
        p10_10_32_105, 
        p10_10_32_106, 
        p10_10_32_107, 
        p10_10_32_108, 
        p10_10_32_109, 
        p10_10_32_110, 
        p10_10_32_111, 
        p10_10_32_112, 
        p10_10_32_113, 
        p10_10_32_114, 
        p10_10_32_115, 
        p10_10_32_116, 
        p10_10_32_117, 
        p10_10_32_118, 
        p10_10_32_119, 
        p10_10_32_120, 
        p10_10_32_121, 
        p10_10_32_122, 
        p10_10_32_123, 
        p10_10_32_124, 
        p10_10_32_125, 
        p10_10_32_126, 
        p10_10_32_127, 
        p10_10_32_128, 
        p10_10_32_129, 
        p10_10_32_130, 
        p10_10_32_131;
    Link l10_20_64_34, 
        l10_20_64_29, 
        l10_20_64_35, 
        l10_20_64_28, 
        l10_20_64_31, 
        l10_20_64_30, 
        l10_20_64_33, 
        l10_20_64_32, 
        l10_20_64_0, 
        l10_20_64_1, 
        l10_20_64_2, 
        l10_20_64_3, 
        l10_20_64_4, 
        l10_20_64_5, 
        l10_20_64_6, 
        l10_20_64_7, 
        l10_20_64_8, 
        l10_20_64_9, 
        l10_20_64_10, 
        l10_20_64_11, 
        l10_20_64_12, 
        l10_20_64_13, 
        l10_20_64_14, 
        l10_20_64_15, 
        l10_20_64_16, 
        l10_20_64_17, 
        l10_20_64_18, 
        l10_20_64_19, 
        l10_20_64_20, 
        l10_20_64_21, 
        l10_20_64_22, 
        l10_20_64_23, 
        l10_20_64_24, 
        l10_20_64_25, 
        l10_20_64_26, 
        l10_20_64_27, 
        l10_10_64_66, 
        l10_10_64_67, 
        l10_10_64_68, 
        l10_10_64_69, 
        l10_10_64_89, 
        l10_10_64_88, 
        l10_10_64_80, 
        l10_10_64_81, 
        l10_10_64_82, 
        l10_10_64_83, 
        l10_10_64_84, 
        l10_10_64_85, 
        l10_10_64_86, 
        l10_10_64_87, 
        l10_10_64_79, 
        l10_10_64_77, 
        l10_20_64_36, 
        l10_10_64_78, 
        l10_10_64_76, 
        l10_10_64_75, 
        l10_10_64_74, 
        l10_10_64_73, 
        l10_10_64_72, 
        l10_10_64_71, 
        l10_10_64_70, 
        l10_10_64_0, 
        l10_10_64_1, 
        l10_10_64_2, 
        l10_10_64_3, 
        l10_10_64_4, 
        l10_10_64_5, 
        l10_10_64_6, 
        l10_10_64_7, 
        l10_10_64_8, 
        l10_10_64_9, 
        l10_10_64_10, 
        l10_10_64_11, 
        l10_10_64_12, 
        l10_10_64_13, 
        l10_10_64_14, 
        l10_10_64_15, 
        l10_10_64_16, 
        l10_10_64_17, 
        l10_10_64_18, 
        l10_10_64_27, 
        l10_10_64_19, 
        l10_10_64_20, 
        l10_10_64_21, 
        l10_10_64_22, 
        l10_10_64_23, 
        l10_10_64_24, 
        l10_10_64_25, 
        l10_10_64_26, 
        l10_10_64_28, 
        l10_10_64_29, 
        l10_10_64_30, 
        l10_10_64_31, 
        l10_10_64_32, 
        l10_10_64_33, 
        l10_10_64_34, 
        l10_10_64_35, 
        l10_10_64_36, 
        l10_10_64_37, 
        l10_10_64_38, 
        l10_10_64_39, 
        l10_10_64_40, 
        l10_10_64_41, 
        l10_10_64_42, 
        l10_10_64_43, 
        l10_10_64_44, 
        l10_10_64_45, 
        l10_10_64_46, 
        l10_10_64_47, 
        l10_10_64_48, 
        l10_10_64_49, 
        l10_10_64_50, 
        l10_10_64_51, 
        l10_10_64_52, 
        l10_10_64_53, 
        l10_10_64_54, 
        l10_10_64_55, 
        l10_10_64_56, 
        l10_10_64_57, 
        l10_10_64_58, 
        l10_10_64_59, 
        l10_10_64_60, 
        l10_10_64_61, 
        l10_10_64_62, 
        l10_10_64_63, 
        l10_10_64_64, 
        l10_10_64_65;
    
    List<AdminDomain> ads;
    List<Node> ns;
    List<Link> ls;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cliente8_usp = new AdminDomain("cliente8_usp", true);
        cliente2_cpqd = new AdminDomain("cliente2_cpqd", true);
        cliente7_usp = new AdminDomain("cliente7_usp", true);
        cliente1_cpqd = new AdminDomain("cliente1_cpqd", true);
        cliente4_rnprj = new AdminDomain("cliente4_rnprj", true);
        cliente3_rnprj = new AdminDomain("cliente3_rnprj", true);
        cliente6_ufrj = new AdminDomain("cliente6_ufrj", true);
        cliente5_ufrj = new AdminDomain("cliente5_ufrj", true);
        UFPA = new AdminDomain("UFPA", false);
        cliente1 = new AdminDomain("cliente1", true);
        cliente2 = new AdminDomain("cliente2", true);
        cliente3 = new AdminDomain("cliente3", true);
        cliente4 = new AdminDomain("cliente4", true);
        cliente24 = new AdminDomain("cliente24", true);
        cliente23 = new AdminDomain("cliente23", true);
        cliente15 = new AdminDomain("cliente15", true);
        cliente16 = new AdminDomain("cliente16", true);
        cliente17 = new AdminDomain("cliente17", true);
        cliente18 = new AdminDomain("cliente18", true);
        cliente19 = new AdminDomain("cliente19", true);
        cliente20 = new AdminDomain("cliente20", true);
        cliente21 = new AdminDomain("cliente21", true);
        cliente22 = new AdminDomain("cliente22", true);
        cliente14 = new AdminDomain("cliente14", true);
        cliente12 = new AdminDomain("cliente12", true);
        cliente13 = new AdminDomain("cliente13", true);
        cliente11 = new AdminDomain("cliente11", true);
        cliente10 = new AdminDomain("cliente10", true);
        cliente9 = new AdminDomain("cliente9", true);
        cliente8 = new AdminDomain("cliente8", true);
        cliente7 = new AdminDomain("cliente7", true);
        cliente6 = new AdminDomain("cliente6", true);
        cliente5 = new AdminDomain("cliente5", true);
        UFRJ = new AdminDomain("UFRJ", false);

        pd_cliente8_usp = new ProvisioningDomain("pd_cliente8_usp", "PIP", cliente8_usp);
        pd_cliente2_cpqd = new ProvisioningDomain("pd_cliente2_cpqd", "PIP", cliente2_cpqd);
        pd_cliente7_usp = new ProvisioningDomain("pd_cliente7_usp", "PIP", cliente7_usp);
        pd_cliente1_cpqd = new ProvisioningDomain("pd_cliente1_cpqd", "PIP", cliente1_cpqd);
        pd_cliente4_rnprj = new ProvisioningDomain("pd_cliente4_rnprj", "PIP", cliente4_rnprj);
        pd_cliente3_rnprj = new ProvisioningDomain("pd_cliente3_rnprj", "PIP", cliente3_rnprj);
        pd_cliente6_ufrj = new ProvisioningDomain("pd_cliente6_ufrj", "PIP", cliente6_ufrj);
        pd_cliente5_ufrj = new ProvisioningDomain("pd_cliente5_ufrj", "PIP", cliente5_ufrj);
        pd_UFPA = new ProvisioningDomain("pd_UFPA", "PIP", UFPA);
        pd_cliente1 = new ProvisioningDomain("pd_cliente1", "PIP", cliente1);
        pd_cliente2 = new ProvisioningDomain("pd_cliente2", "PIP", cliente2);
        pd_cliente3 = new ProvisioningDomain("pd_cliente3", "PIP", cliente3);
        pd_cliente4 = new ProvisioningDomain("pd_cliente4", "PIP", cliente4);
        pd_cliente24 = new ProvisioningDomain("pd_cliente24", "PIP", cliente24);
        pd_cliente23 = new ProvisioningDomain("pd_cliente23", "PIP", cliente23);
        pd_cliente15 = new ProvisioningDomain("pd_cliente15", "PIP", cliente15);
        pd_cliente16 = new ProvisioningDomain("pd_cliente16", "PIP", cliente16);
        pd_cliente17 = new ProvisioningDomain("pd_cliente17", "PIP", cliente17);
        pd_cliente18 = new ProvisioningDomain("pd_cliente18", "PIP", cliente18);
        pd_cliente19 = new ProvisioningDomain("pd_cliente19", "PIP", cliente19);
        pd_cliente20 = new ProvisioningDomain("pd_cliente20", "PIP", cliente20);
        pd_cliente21 = new ProvisioningDomain("pd_cliente21", "PIP", cliente21);
        pd_cliente22 = new ProvisioningDomain("pd_cliente22", "PIP", cliente22);
        pd_cliente14 = new ProvisioningDomain("pd_cliente14", "PIP", cliente14);
        pd_cliente12 = new ProvisioningDomain("pd_cliente12", "PIP", cliente12);
        pd_cliente13 = new ProvisioningDomain("pd_cliente13", "PIP", cliente13);
        pd_cliente11 = new ProvisioningDomain("pd_cliente11", "PIP", cliente11);
        pd_cliente10 = new ProvisioningDomain("pd_cliente10", "PIP", cliente10);
        pd_cliente9 = new ProvisioningDomain("pd_cliente9", "PIP", cliente9);
        pd_cliente8 = new ProvisioningDomain("pd_cliente8", "PIP", cliente8);
        pd_cliente7 = new ProvisioningDomain("pd_cliente7", "PIP", cliente7);
        pd_cliente6 = new ProvisioningDomain("pd_cliente6", "PIP", cliente6);
        pd_cliente5 = new ProvisioningDomain("pd_cliente5", "PIP", cliente5);
        pd_UFRJ = new ProvisioningDomain("pd_UFRJ", "PIP", UFRJ);

        n10_20_0_12   = new Node("PSC", "n10_20_0_12", "n10_20_0_12", pd_UFRJ);
        n10_20_0_13   = new Node("PSC", "n10_20_0_13", "n10_20_0_13", pd_cliente8_usp);
        n10_20_0_3    = new Node("PSC", "n10_20_0_3", "n10_20_0_3", pd_cliente2_cpqd);
        n10_20_0_14   = new Node("PSC", "n10_20_0_14", "n10_20_0_14", pd_UFRJ);
        n10_20_0_15   = new Node("PSC", "n10_20_0_15", "n10_20_0_15", pd_cliente7_usp);
        n10_20_0_0    = new Node("PSC", "n10_20_0_0", "n10_20_0_0", pd_UFRJ);
        n10_20_0_1    = new Node("PSC", "n10_20_0_1", "n10_20_0_1", pd_cliente1_cpqd);
        n10_20_0_6    = new Node("PSC", "n10_20_0_6", "n10_20_0_6", pd_UFRJ);
        n10_20_0_7    = new Node("PSC", "n10_20_0_7", "n10_20_0_7", pd_cliente4_rnprj);
        n10_20_0_4    = new Node("PSC", "n10_20_0_4", "n10_20_0_4", pd_UFRJ);
        n10_20_0_5    = new Node("PSC", "n10_20_0_5", "n10_20_0_5", pd_cliente3_rnprj);
        n10_20_0_10   = new Node("PSC", "n10_20_0_10", "n10_20_0_10", pd_UFRJ);
        n10_20_0_11   = new Node("PSC", "n10_20_0_11", "n10_20_0_11", pd_cliente6_ufrj);
        n10_20_0_8    = new Node("PSC", "n10_20_0_8", "n10_20_0_8", pd_UFRJ);
        n10_20_0_9    = new Node("PSC", "n10_20_0_9", "n10_20_0_9", pd_cliente5_ufrj);
        n10_10_0_1    = new Node("PSC", "n10_10_0_1", "n10_10_0_1", pd_UFPA);
        n10_10_0_2    = new Node("PSC", "n10_10_0_2", "n10_10_0_2", pd_cliente1);
        n10_10_0_3    = new Node("PSC", "n10_10_0_3", "n10_10_0_3", pd_cliente2);
        n10_10_0_4    = new Node("PSC", "n10_10_0_4", "n10_10_0_4", pd_UFPA);
        n10_10_0_5    = new Node("PSC", "n10_10_0_5", "n10_10_0_5", pd_cliente3);
        n10_10_0_6    = new Node("PSC", "n10_10_0_6", "n10_10_0_6", pd_cliente4);
        n10_10_0_33   = new Node("PSC", "n10_10_0_33", "n10_10_0_33", pd_UFPA);
        n10_10_0_35   = new Node("PSC", "n10_10_0_35", "n10_10_0_35", pd_cliente24);
        n10_10_0_34   = new Node("PSC", "n10_10_0_34", "n10_10_0_34", pd_cliente23);
        n10_10_0_22   = new Node("PSC", "n10_10_0_22", "n10_10_0_22", pd_UFPA);
        n10_10_0_23   = new Node("PSC", "n10_10_0_23", "n10_10_0_23", pd_cliente15);
        n10_10_0_24   = new Node("PSC", "n10_10_0_24", "n10_10_0_24", pd_cliente16);
        n10_10_0_25   = new Node("PSC", "n10_10_0_25", "n10_10_0_25", pd_UFPA);
        n10_10_0_26   = new Node("PSC", "n10_10_0_26", "n10_10_0_26", pd_cliente17);
        n10_10_0_27   = new Node("PSC", "n10_10_0_27", "n10_10_0_27", pd_cliente18);
        n10_10_0_28   = new Node("PSC", "n10_10_0_28", "n10_10_0_28", pd_UFPA);
        n10_10_0_29   = new Node("PSC", "n10_10_0_29", "n10_10_0_29", pd_cliente19);
        n10_10_0_30   = new Node("PSC", "n10_10_0_30", "n10_10_0_30", pd_cliente20);
        n10_10_0_0    = new Node("PSC", "n10_10_0_0", "n10_10_0_0", pd_UFPA);
        n10_10_0_31   = new Node("PSC", "n10_10_0_31", "n10_10_0_31", pd_cliente21);
        n10_10_0_32   = new Node("PSC", "n10_10_0_32", "n10_10_0_32", pd_cliente22);
        n10_10_0_19   = new Node("PSC", "n10_10_0_19", "n10_10_0_19", pd_UFPA);
        n10_10_0_21   = new Node("PSC", "n10_10_0_21", "n10_10_0_21", pd_cliente14);
        n10_10_0_16   = new Node("PSC", "n10_10_0_16", "n10_10_0_16", pd_UFPA);
        n10_10_0_18   = new Node("PSC", "n10_10_0_18", "n10_10_0_18", pd_cliente12);
        n10_10_0_20   = new Node("PSC", "n10_10_0_20", "n10_10_0_20", pd_cliente13);
        n10_10_0_17   = new Node("PSC", "n10_10_0_17", "n10_10_0_17", pd_cliente11);
        n10_10_0_13   = new Node("PSC", "n10_10_0_13", "n10_10_0_13", pd_UFPA);
        n10_10_0_15   = new Node("PSC", "n10_10_0_15", "n10_10_0_15", pd_cliente10);
        n10_10_0_14   = new Node("PSC", "n10_10_0_14", "n10_10_0_14", pd_cliente9);
        n10_10_0_10   = new Node("PSC", "n10_10_0_10", "n10_10_0_10", pd_UFPA);
        n10_10_0_12   = new Node("PSC", "n10_10_0_12", "n10_10_0_12", pd_cliente8);
        n10_10_0_11   = new Node("PSC", "n10_10_0_11", "n10_10_0_11", pd_cliente7);
        n10_10_0_7    = new Node("PSC", "n10_10_0_7", "n10_10_0_7", pd_UFPA);
        n10_10_0_9    = new Node("PSC", "n10_10_0_9", "n10_10_0_9", pd_cliente6);
        n10_10_0_8    = new Node("PSC", "n10_10_0_8", "n10_10_0_8", pd_cliente5);
        n10_20_0_2    = new Node("PSC", "n10_20_0_2", "n10_20_0_2", pd_UFRJ);

        p10_20_32_62  = new Port("10_20_32_62", "IP", false, n10_20_0_12);
        p10_20_32_71  = new Port("10_20_32_71", "IP", false, n10_20_0_13);
        p10_20_32_57  = new Port("10_20_32_57", "IP", false, n10_20_0_2);
        p10_20_32_66  = new Port("10_20_32_66", "IP", false, n10_20_0_3);
        p10_20_32_63  = new Port("10_20_32_63", "IP", false, n10_20_0_14);
        p10_20_32_72  = new Port("10_20_32_72", "IP", false, n10_20_0_15);
        p10_20_32_56  = new Port("10_20_32_56", "IP", false, n10_20_0_0);
        p10_20_32_65  = new Port("10_20_32_65", "IP", false, n10_20_0_1);
        p10_20_32_59  = new Port("10_20_32_59", "IP", false, n10_20_0_6);
        p10_20_32_68  = new Port("10_20_32_68", "IP", false, n10_20_0_7);
        p10_20_32_58  = new Port("10_20_32_58", "IP", false, n10_20_0_4);
        p10_20_32_67  = new Port("10_20_32_67", "IP", false, n10_20_0_5);
        p10_20_32_61  = new Port("10_20_32_61", "IP", false, n10_20_0_10);
        p10_20_32_70  = new Port("10_20_32_70", "IP", false, n10_20_0_11);
        p10_20_32_60  = new Port("10_20_32_60", "IP", false, n10_20_0_8);
        p10_20_32_69  = new Port("10_20_32_69", "IP", false, n10_20_0_9);
        p10_20_32_0   = new Port("10_20_32_0", "IP", false, n10_20_0_4);
        p10_20_32_1   = new Port("10_20_32_1", "IP", false, n10_20_0_6);
        p10_20_32_2   = new Port("10_20_32_2", "IP", false, n10_20_0_4);
        p10_20_32_3   = new Port("10_20_32_3", "IP", false, n10_20_0_0);
        p10_20_32_4   = new Port("10_20_32_4", "IP", false, n10_20_0_4);
        p10_20_32_5   = new Port("10_20_32_5", "IP", false, n10_20_0_2);
        p10_20_32_6   = new Port("10_20_32_6", "IP", false, n10_20_0_4);
        p10_20_32_7   = new Port("10_20_32_7", "IP", false, n10_20_0_14);
        p10_20_32_8   = new Port("10_20_32_8", "IP", false, n10_20_0_4);
        p10_20_32_9   = new Port("10_20_32_9", "IP", false, n10_20_0_12);
        p10_20_32_10  = new Port("10_20_32_10", "IP", false, n10_20_0_4);
        p10_20_32_11  = new Port("10_20_32_11", "IP", false, n10_20_0_10);
        p10_20_32_12  = new Port("10_20_32_12", "IP", false, n10_20_0_4);
        p10_20_32_13  = new Port("10_20_32_13", "IP", false, n10_20_0_8);
        p10_20_32_14  = new Port("10_20_32_14", "IP", false, n10_20_0_6);
        p10_20_32_15  = new Port("10_20_32_15", "IP", false, n10_20_0_0);
        p10_20_32_16  = new Port("10_20_32_16", "IP", false, n10_20_0_6);
        p10_20_32_17  = new Port("10_20_32_17", "IP", false, n10_20_0_2);
        p10_20_32_18  = new Port("10_20_32_18", "IP", false, n10_20_0_6);
        p10_20_32_19  = new Port("10_20_32_19", "IP", false, n10_20_0_14);
        p10_20_32_20  = new Port("10_20_32_20", "IP", false, n10_20_0_6);
        p10_20_32_21  = new Port("10_20_32_21", "IP", false, n10_20_0_12);
        p10_20_32_22  = new Port("10_20_32_22", "IP", false, n10_20_0_6);
        p10_20_32_23  = new Port("10_20_32_23", "IP", false, n10_20_0_10);
        p10_20_32_24  = new Port("10_20_32_24", "IP", false, n10_20_0_6);
        p10_20_32_25  = new Port("10_20_32_25", "IP", false, n10_20_0_8);
        p10_20_32_26  = new Port("10_20_32_26", "IP", false, n10_20_0_0);
        p10_20_32_27  = new Port("10_20_32_27", "IP", false, n10_20_0_2);
        p10_20_32_28  = new Port("10_20_32_28", "IP", false, n10_20_0_0);
        p10_20_32_29  = new Port("10_20_32_29", "IP", false, n10_20_0_14);
        p10_20_32_30  = new Port("10_20_32_30", "IP", false, n10_20_0_0);
        p10_20_32_31  = new Port("10_20_32_31", "IP", false, n10_20_0_12);
        p10_20_32_32  = new Port("10_20_32_32", "IP", false, n10_20_0_0);
        p10_20_32_33  = new Port("10_20_32_33", "IP", false, n10_20_0_10);
        p10_20_32_34  = new Port("10_20_32_34", "IP", false, n10_20_0_0);
        p10_20_32_35  = new Port("10_20_32_35", "IP", false, n10_20_0_8);
        p10_20_32_36  = new Port("10_20_32_36", "IP", false, n10_20_0_2);
        p10_20_32_37  = new Port("10_20_32_37", "IP", false, n10_20_0_14);
        p10_20_32_38  = new Port("10_20_32_38", "IP", false, n10_20_0_2);
        p10_20_32_39  = new Port("10_20_32_39", "IP", false, n10_20_0_12);
        p10_20_32_40  = new Port("10_20_32_40", "IP", false, n10_20_0_2);
        p10_20_32_41  = new Port("10_20_32_41", "IP", false, n10_20_0_10);
        p10_20_32_42  = new Port("10_20_32_42", "IP", false, n10_20_0_2);
        p10_20_32_43  = new Port("10_20_32_43", "IP", false, n10_20_0_8);
        p10_20_32_44  = new Port("10_20_32_44", "IP", false, n10_20_0_14);
        p10_20_32_45  = new Port("10_20_32_45", "IP", false, n10_20_0_12);
        p10_20_32_46  = new Port("10_20_32_46", "IP", false, n10_20_0_14);
        p10_20_32_47  = new Port("10_20_32_47", "IP", false, n10_20_0_10);
        p10_20_32_48  = new Port("10_20_32_48", "IP", false, n10_20_0_14);
        p10_20_32_49  = new Port("10_20_32_49", "IP", false, n10_20_0_8);
        p10_20_32_50  = new Port("10_20_32_50", "IP", false, n10_20_0_12);
        p10_20_32_51  = new Port("10_20_32_51", "IP", false, n10_20_0_10);
        p10_20_32_52  = new Port("10_20_32_52", "IP", false, n10_20_0_12);
        p10_20_32_53  = new Port("10_20_32_53", "IP", false, n10_20_0_8);
        p10_20_32_54  = new Port("10_20_32_54", "IP", false, n10_20_0_10);
        p10_20_32_55  = new Port("10_20_32_55", "IP", false, n10_20_0_8);
        p10_10_32_133 = new Port("10_10_32_133", "IP", false, n10_10_0_1);
        p10_10_32_157 = new Port("10_10_32_157", "IP", false, n10_10_0_2);
        p10_10_32_134 = new Port("10_10_32_134", "IP", false, n10_10_0_1);
        p10_10_32_158 = new Port("10_10_32_158", "IP", false, n10_10_0_3);
        p10_10_32_135 = new Port("10_10_32_135", "IP", false, n10_10_0_4);
        p10_10_32_159 = new Port("10_10_32_159", "IP", false, n10_10_0_5);
        p10_10_32_136 = new Port("10_10_32_136", "IP", false, n10_10_0_4);
        p10_10_32_160 = new Port("10_10_32_160", "IP", false, n10_10_0_6);
        p10_10_32_156 = new Port("10_10_32_156", "IP", false, n10_10_0_33);
        p10_10_32_180 = new Port("10_10_32_180", "IP", false, n10_10_0_35);
        p10_10_32_155 = new Port("10_10_32_155", "IP", false, n10_10_0_33);
        p10_10_32_179 = new Port("10_10_32_179", "IP", false, n10_10_0_34);
        p10_10_32_147 = new Port("10_10_32_147", "IP", false, n10_10_0_22);
        p10_10_32_171 = new Port("10_10_32_171", "IP", false, n10_10_0_23);
        p10_10_32_148 = new Port("10_10_32_148", "IP", false, n10_10_0_22);
        p10_10_32_172 = new Port("10_10_32_172", "IP", false, n10_10_0_24);
        p10_10_32_149 = new Port("10_10_32_149", "IP", false, n10_10_0_25);
        p10_10_32_173 = new Port("10_10_32_173", "IP", false, n10_10_0_26);
        p10_10_32_150 = new Port("10_10_32_150", "IP", false, n10_10_0_25);
        p10_10_32_174 = new Port("10_10_32_174", "IP", false, n10_10_0_27);
        p10_10_32_151 = new Port("10_10_32_151", "IP", false, n10_10_0_28);
        p10_10_32_175 = new Port("10_10_32_175", "IP", false, n10_10_0_29);
        p10_10_32_152 = new Port("10_10_32_152", "IP", false, n10_10_0_28);
        p10_10_32_176 = new Port("10_10_32_176", "IP", false, n10_10_0_30);
        p10_10_32_153 = new Port("10_10_32_153", "IP", false, n10_10_0_0);
        p10_10_32_177 = new Port("10_10_32_177", "IP", false, n10_10_0_31);
        p10_10_32_154 = new Port("10_10_32_154", "IP", false, n10_10_0_0);
        p10_10_32_178 = new Port("10_10_32_178", "IP", false, n10_10_0_32);
        p10_10_32_146 = new Port("10_10_32_146", "IP", false, n10_10_0_19);
        p10_10_32_170 = new Port("10_10_32_170", "IP", false, n10_10_0_21);
        p10_10_32_144 = new Port("10_10_32_144", "IP", false, n10_10_0_16);
        p10_10_32_168 = new Port("10_10_32_168", "IP", false, n10_10_0_18);
        p10_10_32_132 = new Port("10_10_32_132", "IP", false, n10_10_0_0);
        p10_20_32_64  = new Port("10_20_32_64", "IP", false, n10_20_0_2);
        p10_10_32_145 = new Port("10_10_32_145", "IP", false, n10_10_0_19);
        p10_10_32_169 = new Port("10_10_32_169", "IP", false, n10_10_0_20);
        p10_10_32_143 = new Port("10_10_32_143", "IP", false, n10_10_0_16);
        p10_10_32_167 = new Port("10_10_32_167", "IP", false, n10_10_0_17);
        p10_10_32_142 = new Port("10_10_32_142", "IP", false, n10_10_0_13);
        p10_10_32_166 = new Port("10_10_32_166", "IP", false, n10_10_0_15);
        p10_10_32_141 = new Port("10_10_32_141", "IP", false, n10_10_0_13);
        p10_10_32_165 = new Port("10_10_32_165", "IP", false, n10_10_0_14);
        p10_10_32_140 = new Port("10_10_32_140", "IP", false, n10_10_0_10);
        p10_10_32_164 = new Port("10_10_32_164", "IP", false, n10_10_0_12);
        p10_10_32_38  = new Port("10_10_32_38", "IP", false, n10_10_0_10);
        p10_10_32_39  = new Port("10_10_32_39", "IP", false, n10_10_0_25);
        p10_10_32_40  = new Port("10_10_32_40", "IP", false, n10_10_0_10);
        p10_10_32_41  = new Port("10_10_32_41", "IP", false, n10_10_0_28);
        p10_10_32_42  = new Port("10_10_32_42", "IP", false, n10_10_0_1);
        p10_10_32_43  = new Port("10_10_32_43", "IP", false, n10_10_0_4);
        p10_10_32_44  = new Port("10_10_32_44", "IP", false, n10_10_0_1);
        p10_10_32_45  = new Port("10_10_32_45", "IP", false, n10_10_0_19);
        p10_10_32_46  = new Port("10_10_32_46", "IP", false, n10_10_0_1);
        p10_10_32_47  = new Port("10_10_32_47", "IP", false, n10_10_0_22);
        p10_10_32_48  = new Port("10_10_32_48", "IP", false, n10_10_0_1);
        p10_10_32_49  = new Port("10_10_32_49", "IP", false, n10_10_0_13);
        p10_10_32_50  = new Port("10_10_32_50", "IP", false, n10_10_0_1);
        p10_10_32_51  = new Port("10_10_32_51", "IP", false, n10_10_0_16);
        p10_10_32_52  = new Port("10_10_32_52", "IP", false, n10_10_0_1);
        p10_10_32_53  = new Port("10_10_32_53", "IP", false, n10_10_0_33);
        p10_10_32_56  = new Port("10_10_32_56", "IP", false, n10_10_0_1);
        p10_10_32_57  = new Port("10_10_32_57", "IP", false, n10_10_0_25);
        p10_10_32_58  = new Port("10_10_32_58", "IP", false, n10_10_0_1);
        p10_10_32_59  = new Port("10_10_32_59", "IP", false, n10_10_0_28);
        p10_10_32_60  = new Port("10_10_32_60", "IP", false, n10_10_0_4);
        p10_10_32_61  = new Port("10_10_32_61", "IP", false, n10_10_0_19);
        p10_10_32_62  = new Port("10_10_32_62", "IP", false, n10_10_0_4);
        p10_10_32_63  = new Port("10_10_32_63", "IP", false, n10_10_0_22);
        p10_10_32_64  = new Port("10_10_32_64", "IP", false, n10_10_0_4);
        p10_10_32_65  = new Port("10_10_32_65", "IP", false, n10_10_0_13);
        p10_10_32_66  = new Port("10_10_32_66", "IP", false, n10_10_0_4);
        p10_10_32_67  = new Port("10_10_32_67", "IP", false, n10_10_0_16);
        p10_10_32_68  = new Port("10_10_32_68", "IP", false, n10_10_0_4);
        p10_10_32_69  = new Port("10_10_32_69", "IP", false, n10_10_0_33);
        p10_10_32_70  = new Port("10_10_32_70", "IP", false, n10_10_0_4);
        p10_10_32_71  = new Port("10_10_32_71", "IP", false, n10_10_0_0);
        p10_10_32_72  = new Port("10_10_32_72", "IP", false, n10_10_0_4);
        p10_10_32_73  = new Port("10_10_32_73", "IP", false, n10_10_0_25);
        p10_10_32_74  = new Port("10_10_32_74", "IP", false, n10_10_0_4);
        p10_10_32_75  = new Port("10_10_32_75", "IP", false, n10_10_0_28);
        p10_10_32_139 = new Port("10_10_32_139", "IP", false, n10_10_0_10);
        p10_10_32_163 = new Port("10_10_32_163", "IP", false, n10_10_0_11);
        p10_10_32_138 = new Port("10_10_32_138", "IP", false, n10_10_0_7);
        p10_10_32_162 = new Port("10_10_32_162", "IP", false, n10_10_0_9);
        p10_10_32_137 = new Port("10_10_32_137", "IP", false, n10_10_0_7);
        p10_10_32_161 = new Port("10_10_32_161", "IP", false, n10_10_0_8);
        p10_10_32_0   = new Port("10_10_32_0", "IP", false, n10_10_0_7);
        p10_10_32_1   = new Port("10_10_32_1", "IP", false, n10_10_0_10);
        p10_10_32_2   = new Port("10_10_32_2", "IP", false, n10_10_0_7);
        p10_10_32_3   = new Port("10_10_32_3", "IP", false, n10_10_0_1);
        p10_10_32_4   = new Port("10_10_32_4", "IP", false, n10_10_0_7);
        p10_10_32_5   = new Port("10_10_32_5", "IP", false, n10_10_0_4);
        p10_10_32_6   = new Port("10_10_32_6", "IP", false, n10_10_0_7);
        p10_10_32_7   = new Port("10_10_32_7", "IP", false, n10_10_0_19);
        p10_10_32_8   = new Port("10_10_32_8", "IP", false, n10_10_0_7);
        p10_10_32_9   = new Port("10_10_32_9", "IP", false, n10_10_0_22);
        p10_10_32_10  = new Port("10_10_32_10", "IP", false, n10_10_0_7);
        p10_10_32_11  = new Port("10_10_32_11", "IP", false, n10_10_0_13);
        p10_10_32_12  = new Port("10_10_32_12", "IP", false, n10_10_0_7);
        p10_10_32_13  = new Port("10_10_32_13", "IP", false, n10_10_0_16);
        p10_10_32_14  = new Port("10_10_32_14", "IP", false, n10_10_0_7);
        p10_10_32_15  = new Port("10_10_32_15", "IP", false, n10_10_0_33);
        p10_10_32_16  = new Port("10_10_32_16", "IP", false, n10_10_0_7);
        p10_10_32_17  = new Port("10_10_32_17", "IP", false, n10_10_0_0);
        p10_10_32_18  = new Port("10_10_32_18", "IP", false, n10_10_0_7);
        p10_10_32_19  = new Port("10_10_32_19", "IP", false, n10_10_0_25);
        p10_10_32_20  = new Port("10_10_32_20", "IP", false, n10_10_0_7);
        p10_10_32_21  = new Port("10_10_32_21", "IP", false, n10_10_0_28);
        p10_10_32_22  = new Port("10_10_32_22", "IP", false, n10_10_0_10);
        p10_10_32_23  = new Port("10_10_32_23", "IP", false, n10_10_0_1);
        p10_10_32_24  = new Port("10_10_32_24", "IP", false, n10_10_0_10);
        p10_10_32_25  = new Port("10_10_32_25", "IP", false, n10_10_0_4);
        p10_10_32_26  = new Port("10_10_32_26", "IP", false, n10_10_0_10);
        p10_10_32_27  = new Port("10_10_32_27", "IP", false, n10_10_0_19);
        p10_10_32_28  = new Port("10_10_32_28", "IP", false, n10_10_0_10);
        p10_10_32_29  = new Port("10_10_32_29", "IP", false, n10_10_0_22);
        p10_10_32_30  = new Port("10_10_32_30", "IP", false, n10_10_0_10);
        p10_10_32_31  = new Port("10_10_32_31", "IP", false, n10_10_0_13);
        p10_10_32_32  = new Port("10_10_32_32", "IP", false, n10_10_0_10);
        p10_10_32_33  = new Port("10_10_32_33", "IP", false, n10_10_0_16);
        p10_10_32_34  = new Port("10_10_32_34", "IP", false, n10_10_0_10);
        p10_10_32_35  = new Port("10_10_32_35", "IP", false, n10_10_0_33);
        p10_10_32_36  = new Port("10_10_32_36", "IP", false, n10_10_0_10);
        p10_10_32_37  = new Port("10_10_32_37", "IP", false, n10_10_0_0);
        p10_10_32_54  = new Port("10_10_32_54", "IP", false, n10_10_0_1);
        p10_10_32_55  = new Port("10_10_32_55", "IP", false, n10_10_0_0);
        p10_10_32_76  = new Port("10_10_32_76", "IP", false, n10_10_0_19);
        p10_10_32_77  = new Port("10_10_32_77", "IP", false, n10_10_0_22);
        p10_10_32_78  = new Port("10_10_32_78", "IP", false, n10_10_0_19);
        p10_10_32_79  = new Port("10_10_32_79", "IP", false, n10_10_0_13);
        p10_10_32_80  = new Port("10_10_32_80", "IP", false, n10_10_0_19);
        p10_10_32_81  = new Port("10_10_32_81", "IP", false, n10_10_0_16);
        p10_10_32_82  = new Port("10_10_32_82", "IP", false, n10_10_0_19);
        p10_10_32_83  = new Port("10_10_32_83", "IP", false, n10_10_0_33);
        p10_10_32_84  = new Port("10_10_32_84", "IP", false, n10_10_0_19);
        p10_10_32_85  = new Port("10_10_32_85", "IP", false, n10_10_0_0);
        p10_10_32_86  = new Port("10_10_32_86", "IP", false, n10_10_0_19);
        p10_10_32_87  = new Port("10_10_32_87", "IP", false, n10_10_0_25);
        p10_10_32_88  = new Port("10_10_32_88", "IP", false, n10_10_0_19);
        p10_10_32_89  = new Port("10_10_32_89", "IP", false, n10_10_0_28);
        p10_10_32_90  = new Port("10_10_32_90", "IP", false, n10_10_0_22);
        p10_10_32_91  = new Port("10_10_32_91", "IP", false, n10_10_0_13);
        p10_10_32_92  = new Port("10_10_32_92", "IP", false, n10_10_0_22);
        p10_10_32_93  = new Port("10_10_32_93", "IP", false, n10_10_0_16);
        p10_10_32_94  = new Port("10_10_32_94", "IP", false, n10_10_0_22);
        p10_10_32_95  = new Port("10_10_32_95", "IP", false, n10_10_0_33);
        p10_10_32_96  = new Port("10_10_32_96", "IP", false, n10_10_0_22);
        p10_10_32_97  = new Port("10_10_32_97", "IP", false, n10_10_0_0);
        p10_10_32_98  = new Port("10_10_32_98", "IP", false, n10_10_0_22);
        p10_10_32_99  = new Port("10_10_32_99", "IP", false, n10_10_0_25);
        p10_10_32_100 = new Port("10_10_32_100", "IP", false, n10_10_0_22);
        p10_10_32_101 = new Port("10_10_32_101", "IP", false, n10_10_0_28);
        p10_10_32_102 = new Port("10_10_32_102", "IP", false, n10_10_0_13);
        p10_10_32_103 = new Port("10_10_32_103", "IP", false, n10_10_0_16);
        p10_10_32_104 = new Port("10_10_32_104", "IP", false, n10_10_0_13);
        p10_10_32_105 = new Port("10_10_32_105", "IP", false, n10_10_0_33);
        p10_10_32_106 = new Port("10_10_32_106", "IP", false, n10_10_0_13);
        p10_10_32_107 = new Port("10_10_32_107", "IP", false, n10_10_0_0);
        p10_10_32_108 = new Port("10_10_32_108", "IP", false, n10_10_0_13);
        p10_10_32_109 = new Port("10_10_32_109", "IP", false, n10_10_0_25);
        p10_10_32_110 = new Port("10_10_32_110", "IP", false, n10_10_0_13);
        p10_10_32_111 = new Port("10_10_32_111", "IP", false, n10_10_0_28);
        p10_10_32_112 = new Port("10_10_32_112", "IP", false, n10_10_0_16);
        p10_10_32_113 = new Port("10_10_32_113", "IP", false, n10_10_0_33);
        p10_10_32_114 = new Port("10_10_32_114", "IP", false, n10_10_0_16);
        p10_10_32_115 = new Port("10_10_32_115", "IP", false, n10_10_0_0);
        p10_10_32_116 = new Port("10_10_32_116", "IP", false, n10_10_0_16);
        p10_10_32_117 = new Port("10_10_32_117", "IP", false, n10_10_0_25);
        p10_10_32_118 = new Port("10_10_32_118", "IP", false, n10_10_0_16);
        p10_10_32_119 = new Port("10_10_32_119", "IP", false, n10_10_0_28);
        p10_10_32_120 = new Port("10_10_32_120", "IP", false, n10_10_0_33);
        p10_10_32_121 = new Port("10_10_32_121", "IP", false, n10_10_0_0);
        p10_10_32_122 = new Port("10_10_32_122", "IP", false, n10_10_0_33);
        p10_10_32_123 = new Port("10_10_32_123", "IP", false, n10_10_0_25);
        p10_10_32_124 = new Port("10_10_32_124", "IP", false, n10_10_0_33);
        p10_10_32_125 = new Port("10_10_32_125", "IP", false, n10_10_0_28);
        p10_10_32_126 = new Port("10_10_32_126", "IP", false, n10_10_0_0);
        p10_10_32_127 = new Port("10_10_32_127", "IP", false, n10_10_0_25);
        p10_10_32_128 = new Port("10_10_32_128", "IP", false, n10_10_0_0);
        p10_10_32_129 = new Port("10_10_32_129", "IP", false, n10_10_0_28);
        p10_10_32_130 = new Port("10_10_32_130", "IP", false, n10_10_0_25);
        p10_10_32_131 = new Port("10_10_32_131", "IP", false, n10_10_0_28);
        
        l10_20_64_34  = Link.createInterDomainLink(p10_20_32_62, p10_20_32_71, 1000000000);
        l10_20_64_29  = Link.createInterDomainLink(p10_20_32_57, p10_20_32_66, 1000000000);
        l10_20_64_35  = Link.createInterDomainLink(p10_20_32_63, p10_20_32_72, 1000000000);
        l10_20_64_28  = Link.createInterDomainLink(p10_20_32_56, p10_20_32_65, 1000000000);
        l10_20_64_31  = Link.createInterDomainLink(p10_20_32_59, p10_20_32_68, 10000000000L);
        l10_20_64_30  = Link.createInterDomainLink(p10_20_32_58, p10_20_32_67, 10000000000L);
        l10_20_64_33  = Link.createInterDomainLink(p10_20_32_61, p10_20_32_70, 10000000000L);
        l10_20_64_32  = Link.createInterDomainLink(p10_20_32_60, p10_20_32_69, 10000000000L);
        l10_20_64_0   = Link.createVirtualLink(p10_20_32_0, p10_20_32_1 , 1000000000);
        l10_20_64_1   = Link.createVirtualLink(p10_20_32_2, p10_20_32_3 , 1000000000);
        l10_20_64_2   = Link.createVirtualLink(p10_20_32_4, p10_20_32_5 , 1000000000);
        l10_20_64_3   = Link.createVirtualLink(p10_20_32_6, p10_20_32_7 , 1000000000);
        l10_20_64_4   = Link.createVirtualLink(p10_20_32_8, p10_20_32_9 , 1000000000);
        l10_20_64_5   = Link.createVirtualLink(p10_20_32_10, p10_20_32_11, 1000000000);
        l10_20_64_6   = Link.createVirtualLink(p10_20_32_12, p10_20_32_13, 1000000000);
        l10_20_64_7   = Link.createVirtualLink(p10_20_32_14, p10_20_32_15, 1000000000);
        l10_20_64_8   = Link.createVirtualLink(p10_20_32_16, p10_20_32_17, 1000000000);
        l10_20_64_9   = Link.createVirtualLink(p10_20_32_18, p10_20_32_19, 1000000000);
        l10_20_64_10  = Link.createVirtualLink(p10_20_32_20, p10_20_32_21, 1000000000);
        l10_20_64_11  = Link.createVirtualLink(p10_20_32_22, p10_20_32_23, 1000000000);
        l10_20_64_12  = Link.createVirtualLink(p10_20_32_24, p10_20_32_25, 1000000000);
        l10_20_64_13  = Link.createVirtualLink(p10_20_32_26, p10_20_32_27, 1000000000);
        l10_20_64_14  = Link.createVirtualLink(p10_20_32_28, p10_20_32_29, 1000000000);
        l10_20_64_15  = Link.createVirtualLink(p10_20_32_30, p10_20_32_31, 1000000000);
        l10_20_64_16  = Link.createVirtualLink(p10_20_32_32, p10_20_32_33, 1000000000);
        l10_20_64_17  = Link.createVirtualLink(p10_20_32_34, p10_20_32_35, 1000000000);
        l10_20_64_18  = Link.createVirtualLink(p10_20_32_36, p10_20_32_37, 1000000000);
        l10_20_64_19  = Link.createVirtualLink(p10_20_32_38, p10_20_32_39, 1000000000);
        l10_20_64_20  = Link.createVirtualLink(p10_20_32_40, p10_20_32_41, 1000000000);
        l10_20_64_21  = Link.createVirtualLink(p10_20_32_42, p10_20_32_43, 1000000000);
        l10_20_64_22  = Link.createVirtualLink(p10_20_32_44, p10_20_32_45, 1000000000);
        l10_20_64_23  = Link.createVirtualLink(p10_20_32_46, p10_20_32_47, 1000000000);
        l10_20_64_24  = Link.createVirtualLink(p10_20_32_48, p10_20_32_49, 1000000000);
        l10_20_64_25  = Link.createVirtualLink(p10_20_32_50, p10_20_32_51, 1000000000);
        l10_20_64_26  = Link.createVirtualLink(p10_20_32_52, p10_20_32_53, 1000000000);
        l10_20_64_27  = Link.createVirtualLink(p10_20_32_54, p10_20_32_55, 1000000000);
        l10_10_64_66  = Link.createInterDomainLink(p10_10_32_133, p10_10_32_157, 1000000000);
        l10_10_64_67  = Link.createInterDomainLink(p10_10_32_134, p10_10_32_158, 1000000000);
        l10_10_64_68  = Link.createInterDomainLink(p10_10_32_135, p10_10_32_159, 1000000000);
        l10_10_64_69  = Link.createInterDomainLink(p10_10_32_136, p10_10_32_160, 1000000000);
        l10_10_64_89  = Link.createInterDomainLink(p10_10_32_156, p10_10_32_180, 1000000000);
        l10_10_64_88  = Link.createInterDomainLink(p10_10_32_155, p10_10_32_179, 1000000000);
        l10_10_64_80  = Link.createInterDomainLink(p10_10_32_147, p10_10_32_171, 1000000000);
        l10_10_64_81  = Link.createInterDomainLink(p10_10_32_148, p10_10_32_172, 1000000000);
        l10_10_64_82  = Link.createInterDomainLink(p10_10_32_149, p10_10_32_173, 1000000000);
        l10_10_64_83  = Link.createInterDomainLink(p10_10_32_150, p10_10_32_174, 1000000000);
        l10_10_64_84  = Link.createInterDomainLink(p10_10_32_151, p10_10_32_175, 1000000000);
        l10_10_64_85  = Link.createInterDomainLink(p10_10_32_152, p10_10_32_176, 1000000000);
        l10_10_64_86  = Link.createInterDomainLink(p10_10_32_153, p10_10_32_177, 1000000000);
        l10_10_64_87  = Link.createInterDomainLink(p10_10_32_154, p10_10_32_178, 1000000000);
        l10_10_64_79  = Link.createInterDomainLink(p10_10_32_146, p10_10_32_170, 1000000000);
        l10_10_64_77  = Link.createInterDomainLink(p10_10_32_144, p10_10_32_168, 1000000000);
        l10_20_64_36  = Link.createInterDomainLink(p10_10_32_132, p10_20_32_64, 1000000000);
        l10_10_64_78  = Link.createInterDomainLink(p10_10_32_145, p10_10_32_169, 1000000000);
        l10_10_64_76  = Link.createInterDomainLink(p10_10_32_143, p10_10_32_167, 1000000000);
        l10_10_64_75  = Link.createInterDomainLink(p10_10_32_142, p10_10_32_166, 1000000000);
        l10_10_64_74  = Link.createInterDomainLink(p10_10_32_141, p10_10_32_165, 1000000000);
        l10_10_64_73  = Link.createInterDomainLink(p10_10_32_140, p10_10_32_164, 1000000000);
        l10_10_64_72  = Link.createInterDomainLink(p10_10_32_139, p10_10_32_163, 1000000000);
        l10_10_64_71  = Link.createInterDomainLink(p10_10_32_138, p10_10_32_162, 1000000000);
        l10_10_64_70  = Link.createInterDomainLink(p10_10_32_137, p10_10_32_161, 1000000000);
        l10_10_64_0   = Link.createVirtualLink(p10_10_32_0, p10_10_32_1 , 1000000000);
        l10_10_64_1   = Link.createVirtualLink(p10_10_32_2, p10_10_32_3 , 1000000000);
        l10_10_64_2   = Link.createVirtualLink(p10_10_32_4, p10_10_32_5 , 1000000000);
        l10_10_64_3   = Link.createVirtualLink(p10_10_32_6, p10_10_32_7 , 1000000000);
        l10_10_64_4   = Link.createVirtualLink(p10_10_32_8, p10_10_32_9 , 1000000000);
        l10_10_64_5   = Link.createVirtualLink(p10_10_32_10, p10_10_32_11, 1000000000);
        l10_10_64_6   = Link.createVirtualLink(p10_10_32_12, p10_10_32_13, 1000000000);
        l10_10_64_7   = Link.createVirtualLink(p10_10_32_14, p10_10_32_15, 1000000000);
        l10_10_64_8   = Link.createVirtualLink(p10_10_32_16, p10_10_32_17, 1000000000);
        l10_10_64_9   = Link.createVirtualLink(p10_10_32_18, p10_10_32_19, 1000000000);
        l10_10_64_10  = Link.createVirtualLink(p10_10_32_20, p10_10_32_21, 1000000000);
        l10_10_64_11  = Link.createVirtualLink(p10_10_32_22, p10_10_32_23, 1000000000);
        l10_10_64_12  = Link.createVirtualLink(p10_10_32_24, p10_10_32_25, 1000000000);
        l10_10_64_13  = Link.createVirtualLink(p10_10_32_26, p10_10_32_27, 1000000000);
        l10_10_64_14  = Link.createVirtualLink(p10_10_32_28, p10_10_32_29, 1000000000);
        l10_10_64_15  = Link.createVirtualLink(p10_10_32_30, p10_10_32_31, 1000000000);
        l10_10_64_16  = Link.createVirtualLink(p10_10_32_32, p10_10_32_33, 1000000000);
        l10_10_64_17  = Link.createVirtualLink(p10_10_32_34, p10_10_32_35, 1000000000);
        l10_10_64_18  = Link.createVirtualLink(p10_10_32_36, p10_10_32_37, 1000000000);
        l10_10_64_27  = Link.createVirtualLink(p10_10_32_54, p10_10_32_55, 1000000000);
        l10_10_64_19  = Link.createVirtualLink(p10_10_32_38, p10_10_32_39, 1000000000);
        l10_10_64_20  = Link.createVirtualLink(p10_10_32_40, p10_10_32_41, 1000000000);
        l10_10_64_21  = Link.createVirtualLink(p10_10_32_42, p10_10_32_43, 1000000000);
        l10_10_64_22  = Link.createVirtualLink(p10_10_32_44, p10_10_32_45, 1000000000);
        l10_10_64_23  = Link.createVirtualLink(p10_10_32_46, p10_10_32_47, 1000000000);
        l10_10_64_24  = Link.createVirtualLink(p10_10_32_48, p10_10_32_49, 1000000000);
        l10_10_64_25  = Link.createVirtualLink(p10_10_32_50, p10_10_32_51, 1000000000);
        l10_10_64_26  = Link.createVirtualLink(p10_10_32_52, p10_10_32_53, 1000000000);
        l10_10_64_28  = Link.createVirtualLink(p10_10_32_56, p10_10_32_57, 1000000000);
        l10_10_64_29  = Link.createVirtualLink(p10_10_32_58, p10_10_32_59, 1000000000);
        l10_10_64_30  = Link.createVirtualLink(p10_10_32_60, p10_10_32_61, 1000000000);
        l10_10_64_31  = Link.createVirtualLink(p10_10_32_62, p10_10_32_63, 1000000000);
        l10_10_64_32  = Link.createVirtualLink(p10_10_32_64, p10_10_32_65, 1000000000);
        l10_10_64_33  = Link.createVirtualLink(p10_10_32_66, p10_10_32_67, 1000000000);
        l10_10_64_34  = Link.createVirtualLink(p10_10_32_68, p10_10_32_69, 1000000000);
        l10_10_64_35  = Link.createVirtualLink(p10_10_32_70, p10_10_32_71, 1000000000);
        l10_10_64_36  = Link.createVirtualLink(p10_10_32_72, p10_10_32_73, 1000000000);
        l10_10_64_37  = Link.createVirtualLink(p10_10_32_74, p10_10_32_75, 1000000000);
        l10_10_64_38  = Link.createVirtualLink(p10_10_32_76, p10_10_32_77, 1000000000);
        l10_10_64_39  = Link.createVirtualLink(p10_10_32_78, p10_10_32_79, 1000000000);
        l10_10_64_40  = Link.createVirtualLink(p10_10_32_80, p10_10_32_81, 1000000000);
        l10_10_64_41  = Link.createVirtualLink(p10_10_32_82, p10_10_32_83, 1000000000);
        l10_10_64_42  = Link.createVirtualLink(p10_10_32_84, p10_10_32_85, 1000000000);
        l10_10_64_43  = Link.createVirtualLink(p10_10_32_86, p10_10_32_87, 1000000000);
        l10_10_64_44  = Link.createVirtualLink(p10_10_32_88, p10_10_32_89, 1000000000);
        l10_10_64_45  = Link.createVirtualLink(p10_10_32_90, p10_10_32_91, 1000000000);
        l10_10_64_46  = Link.createVirtualLink(p10_10_32_92, p10_10_32_93, 1000000000);
        l10_10_64_47  = Link.createVirtualLink(p10_10_32_94, p10_10_32_95, 1000000000);
        l10_10_64_48  = Link.createVirtualLink(p10_10_32_96, p10_10_32_97, 1000000000);
        l10_10_64_49  = Link.createVirtualLink(p10_10_32_98, p10_10_32_99, 1000000000);
        l10_10_64_50  = Link.createVirtualLink(p10_10_32_100, p10_10_32_101, 1000000000);
        l10_10_64_51  = Link.createVirtualLink(p10_10_32_102, p10_10_32_103, 1000000000);
        l10_10_64_52  = Link.createVirtualLink(p10_10_32_104, p10_10_32_105, 1000000000);
        l10_10_64_53  = Link.createVirtualLink(p10_10_32_106, p10_10_32_107, 1000000000);
        l10_10_64_54  = Link.createVirtualLink(p10_10_32_108, p10_10_32_109, 1000000000);
        l10_10_64_55  = Link.createVirtualLink(p10_10_32_110, p10_10_32_111, 1000000000);
        l10_10_64_56  = Link.createVirtualLink(p10_10_32_112, p10_10_32_113, 1000000000);
        l10_10_64_57  = Link.createVirtualLink(p10_10_32_114, p10_10_32_115, 1000000000);
        l10_10_64_58  = Link.createVirtualLink(p10_10_32_116, p10_10_32_117, 1000000000);
        l10_10_64_59  = Link.createVirtualLink(p10_10_32_118, p10_10_32_119, 1000000000);
        l10_10_64_60  = Link.createVirtualLink(p10_10_32_120, p10_10_32_121, 1000000000);
        l10_10_64_61  = Link.createVirtualLink(p10_10_32_122, p10_10_32_123, 1000000000);
        l10_10_64_62  = Link.createVirtualLink(p10_10_32_124, p10_10_32_125, 1000000000);
        l10_10_64_63  = Link.createVirtualLink(p10_10_32_126, p10_10_32_127, 1000000000);
        l10_10_64_64  = Link.createVirtualLink(p10_10_32_128, p10_10_32_129, 1000000000);
        l10_10_64_65  = Link.createVirtualLink(p10_10_32_130, p10_10_32_131, 1000000000);
        
        l10_20_64_34.setBodID("10_20_64_34");
        l10_20_64_29.setBodID("10_20_64_29");
        l10_20_64_35.setBodID("10_20_64_35");
        l10_20_64_28.setBodID("10_20_64_28");
        l10_20_64_31.setBodID("10_20_64_31");
        l10_20_64_30.setBodID("10_20_64_30");
        l10_20_64_33.setBodID("10_20_64_33");
        l10_20_64_32.setBodID("10_20_64_32");
        l10_20_64_0.setBodID("10_20_64_0");
        l10_20_64_1.setBodID("10_20_64_1");
        l10_20_64_2.setBodID("10_20_64_2");
        l10_20_64_3.setBodID("10_20_64_3");
        l10_20_64_4.setBodID("10_20_64_4");
        l10_20_64_5.setBodID("10_20_64_5");
        l10_20_64_6.setBodID("10_20_64_6");
        l10_20_64_7.setBodID("10_20_64_7");
        l10_20_64_8.setBodID("10_20_64_8");
        l10_20_64_9.setBodID("10_20_64_9");
        l10_20_64_10.setBodID("10_20_64_10");
        l10_20_64_11.setBodID("10_20_64_11");
        l10_20_64_12.setBodID("10_20_64_12");
        l10_20_64_13.setBodID("10_20_64_13");
        l10_20_64_14.setBodID("10_20_64_14");
        l10_20_64_15.setBodID("10_20_64_15");
        l10_20_64_16.setBodID("10_20_64_16");
        l10_20_64_17.setBodID("10_20_64_17");
        l10_20_64_18.setBodID("10_20_64_18");
        l10_20_64_19.setBodID("10_20_64_19");
        l10_20_64_20.setBodID("10_20_64_20");
        l10_20_64_21.setBodID("10_20_64_21");
        l10_20_64_22.setBodID("10_20_64_22");
        l10_20_64_23.setBodID("10_20_64_23");
        l10_20_64_24.setBodID("10_20_64_24");
        l10_20_64_25.setBodID("10_20_64_25");
        l10_20_64_26.setBodID("10_20_64_26");
        l10_20_64_27.setBodID("10_20_64_27");
        l10_10_64_66.setBodID("10_10_64_66");
        l10_10_64_67.setBodID("10_10_64_67");
        l10_10_64_68.setBodID("10_10_64_68");
        l10_10_64_69.setBodID("10_10_64_69");
        l10_10_64_89.setBodID("10_10_64_89");
        l10_10_64_88.setBodID("10_10_64_88");
        l10_10_64_80.setBodID("10_10_64_80");
        l10_10_64_81.setBodID("10_10_64_81");
        l10_10_64_82.setBodID("10_10_64_82");
        l10_10_64_83.setBodID("10_10_64_83");
        l10_10_64_84.setBodID("10_10_64_84");
        l10_10_64_85.setBodID("10_10_64_85");
        l10_10_64_86.setBodID("10_10_64_86");
        l10_10_64_87.setBodID("10_10_64_87");
        l10_10_64_79.setBodID("10_10_64_79");
        l10_10_64_77.setBodID("10_10_64_77");
        l10_20_64_36.setBodID("10_20_64_36");
        l10_10_64_78.setBodID("10_10_64_78");
        l10_10_64_76.setBodID("10_10_64_76");
        l10_10_64_75.setBodID("10_10_64_75");
        l10_10_64_74.setBodID("10_10_64_74");
        l10_10_64_73.setBodID("10_10_64_73");
        l10_10_64_72.setBodID("10_10_64_72");
        l10_10_64_71.setBodID("10_10_64_71");
        l10_10_64_70.setBodID("10_10_64_70");
        l10_10_64_0.setBodID("10_10_64_0");
        l10_10_64_1.setBodID("10_10_64_1");
        l10_10_64_2.setBodID("10_10_64_2");
        l10_10_64_3.setBodID("10_10_64_3");
        l10_10_64_4.setBodID("10_10_64_4");
        l10_10_64_5.setBodID("10_10_64_5");
        l10_10_64_6.setBodID("10_10_64_6");
        l10_10_64_7.setBodID("10_10_64_7");
        l10_10_64_8.setBodID("10_10_64_8");
        l10_10_64_9.setBodID("10_10_64_9");
        l10_10_64_10.setBodID("10_10_64_10");
        l10_10_64_11.setBodID("10_10_64_11");
        l10_10_64_12.setBodID("10_10_64_12");
        l10_10_64_13.setBodID("10_10_64_13");
        l10_10_64_14.setBodID("10_10_64_14");
        l10_10_64_15.setBodID("10_10_64_15");
        l10_10_64_16.setBodID("10_10_64_16");
        l10_10_64_17.setBodID("10_10_64_17");
        l10_10_64_18.setBodID("10_10_64_18");
        l10_10_64_27.setBodID("10_10_64_27");
        l10_10_64_19.setBodID("10_10_64_19");
        l10_10_64_20.setBodID("10_10_64_20");
        l10_10_64_21.setBodID("10_10_64_21");
        l10_10_64_22.setBodID("10_10_64_22");
        l10_10_64_23.setBodID("10_10_64_23");
        l10_10_64_24.setBodID("10_10_64_24");
        l10_10_64_25.setBodID("10_10_64_25");
        l10_10_64_26.setBodID("10_10_64_26");
        l10_10_64_28.setBodID("10_10_64_28");
        l10_10_64_29.setBodID("10_10_64_29");
        l10_10_64_30.setBodID("10_10_64_30");
        l10_10_64_31.setBodID("10_10_64_31");
        l10_10_64_32.setBodID("10_10_64_32");
        l10_10_64_33.setBodID("10_10_64_33");
        l10_10_64_34.setBodID("10_10_64_34");
        l10_10_64_35.setBodID("10_10_64_35");
        l10_10_64_36.setBodID("10_10_64_36");
        l10_10_64_37.setBodID("10_10_64_37");
        l10_10_64_38.setBodID("10_10_64_38");
        l10_10_64_39.setBodID("10_10_64_39");
        l10_10_64_40.setBodID("10_10_64_40");
        l10_10_64_41.setBodID("10_10_64_41");
        l10_10_64_42.setBodID("10_10_64_42");
        l10_10_64_43.setBodID("10_10_64_43");
        l10_10_64_44.setBodID("10_10_64_44");
        l10_10_64_45.setBodID("10_10_64_45");
        l10_10_64_46.setBodID("10_10_64_46");
        l10_10_64_47.setBodID("10_10_64_47");
        l10_10_64_48.setBodID("10_10_64_48");
        l10_10_64_49.setBodID("10_10_64_49");
        l10_10_64_50.setBodID("10_10_64_50");
        l10_10_64_51.setBodID("10_10_64_51");
        l10_10_64_52.setBodID("10_10_64_52");
        l10_10_64_53.setBodID("10_10_64_53");
        l10_10_64_54.setBodID("10_10_64_54");
        l10_10_64_55.setBodID("10_10_64_55");
        l10_10_64_56.setBodID("10_10_64_56");
        l10_10_64_57.setBodID("10_10_64_57");
        l10_10_64_58.setBodID("10_10_64_58");
        l10_10_64_59.setBodID("10_10_64_59");
        l10_10_64_60.setBodID("10_10_64_60");
        l10_10_64_61.setBodID("10_10_64_61");
        l10_10_64_62.setBodID("10_10_64_62");
        l10_10_64_63.setBodID("10_10_64_63");
        l10_10_64_64.setBodID("10_10_64_64");
        l10_10_64_65.setBodID("10_10_64_65");
        
        ads = new ArrayList<AdminDomain>();
        ads.add(cliente8_usp);
        ads.add(cliente2_cpqd);
        ads.add(cliente7_usp);
        ads.add(cliente1_cpqd);
        ads.add(cliente4_rnprj);
        ads.add(cliente3_rnprj);
        ads.add(cliente6_ufrj);
        ads.add(cliente5_ufrj);
        ads.add(UFPA);
        ads.add(cliente1);
        ads.add(cliente2);
        ads.add(cliente3);
        ads.add(cliente4);
        ads.add(cliente24);
        ads.add(cliente23);
        ads.add(cliente15);
        ads.add(cliente16);
        ads.add(cliente17);
        ads.add(cliente18);
        ads.add(cliente19);
        ads.add(cliente20);
        ads.add(cliente21);
        ads.add(cliente22);
        ads.add(cliente14);
        ads.add(cliente12);
        ads.add(cliente13);
        ads.add(cliente11);
        ads.add(cliente10);
        ads.add(cliente9);
        ads.add(cliente8);
        ads.add(cliente7);
        ads.add(cliente6);
        ads.add(cliente5);
        ads.add(UFRJ);
        
        ns = new ArrayList<Node>();
        ns.add(n10_20_0_12);
        ns.add(n10_20_0_13);
        ns.add(n10_20_0_3);
        ns.add(n10_20_0_14);
        ns.add(n10_20_0_15);
        ns.add(n10_20_0_0);
        ns.add(n10_20_0_1);
        ns.add(n10_20_0_6);
        ns.add(n10_20_0_7);
        ns.add(n10_20_0_4);
        ns.add(n10_20_0_5);
        ns.add(n10_20_0_10);
        ns.add(n10_20_0_11);
        ns.add(n10_20_0_8);
        ns.add(n10_20_0_9);
        ns.add(n10_10_0_1);
        ns.add(n10_10_0_2);
        ns.add(n10_10_0_3);
        ns.add(n10_10_0_4);
        ns.add(n10_10_0_5);
        ns.add(n10_10_0_6);
        ns.add(n10_10_0_33);
        ns.add(n10_10_0_35);
        ns.add(n10_10_0_34);
        ns.add(n10_10_0_22);
        ns.add(n10_10_0_23);
        ns.add(n10_10_0_24);
        ns.add(n10_10_0_25);
        ns.add(n10_10_0_26);
        ns.add(n10_10_0_27);
        ns.add(n10_10_0_28);
        ns.add(n10_10_0_29);
        ns.add(n10_10_0_30);
        ns.add(n10_10_0_0);
        ns.add(n10_10_0_31);
        ns.add(n10_10_0_32);
        ns.add(n10_10_0_19);
        ns.add(n10_10_0_21);
        ns.add(n10_10_0_16);
        ns.add(n10_10_0_18);
        ns.add(n10_10_0_20);
        ns.add(n10_10_0_17);
        ns.add(n10_10_0_13);
        ns.add(n10_10_0_15);
        ns.add(n10_10_0_14);
        ns.add(n10_10_0_10);
        ns.add(n10_10_0_12);
        ns.add(n10_10_0_11);
        ns.add(n10_10_0_7);
        ns.add(n10_10_0_9);
        ns.add(n10_10_0_8);
        ns.add(n10_20_0_2);

        ls = new ArrayList<Link>();
        ls.add(l10_20_64_34);
        ls.add(l10_20_64_29);
        ls.add(l10_20_64_35);
        ls.add(l10_20_64_28);
        ls.add(l10_20_64_31);
        ls.add(l10_20_64_30);
        ls.add(l10_20_64_33);
        ls.add(l10_20_64_32);
        ls.add(l10_20_64_0);
        ls.add(l10_20_64_1);
        ls.add(l10_20_64_2);
        ls.add(l10_20_64_3);
        ls.add(l10_20_64_4);
        ls.add(l10_20_64_5);
        ls.add(l10_20_64_6);
        ls.add(l10_20_64_7);
        ls.add(l10_20_64_8);
        ls.add(l10_20_64_9);
        ls.add(l10_20_64_10);
        ls.add(l10_20_64_11);
        ls.add(l10_20_64_12);
        ls.add(l10_20_64_13);
        ls.add(l10_20_64_14);
        ls.add(l10_20_64_15);
        ls.add(l10_20_64_16);
        ls.add(l10_20_64_17);
        ls.add(l10_20_64_18);
        ls.add(l10_20_64_19);
        ls.add(l10_20_64_20);
        ls.add(l10_20_64_21);
        ls.add(l10_20_64_22);
        ls.add(l10_20_64_23);
        ls.add(l10_20_64_24);
        ls.add(l10_20_64_25);
        ls.add(l10_20_64_26);
        ls.add(l10_20_64_27);
        ls.add(l10_10_64_66);
        ls.add(l10_10_64_67);
        ls.add(l10_10_64_68);
        ls.add(l10_10_64_69);
        ls.add(l10_10_64_89);
        ls.add(l10_10_64_88);
        ls.add(l10_10_64_80);
        ls.add(l10_10_64_81);
        ls.add(l10_10_64_82);
        ls.add(l10_10_64_83);
        ls.add(l10_10_64_84);
        ls.add(l10_10_64_85);
        ls.add(l10_10_64_86);
        ls.add(l10_10_64_87);
        ls.add(l10_10_64_79);
        ls.add(l10_10_64_77);
        ls.add(l10_20_64_36);
        ls.add(l10_10_64_78);
        ls.add(l10_10_64_76);
        ls.add(l10_10_64_75);
        ls.add(l10_10_64_74);
        ls.add(l10_10_64_73);
        ls.add(l10_10_64_72);
        ls.add(l10_10_64_71);
        ls.add(l10_10_64_70);
        ls.add(l10_10_64_0);
        ls.add(l10_10_64_1);
        ls.add(l10_10_64_2);
        ls.add(l10_10_64_3);
        ls.add(l10_10_64_4);
        ls.add(l10_10_64_5);
        ls.add(l10_10_64_6);
        ls.add(l10_10_64_7);
        ls.add(l10_10_64_8);
        ls.add(l10_10_64_9);
        ls.add(l10_10_64_10);
        ls.add(l10_10_64_11);
        ls.add(l10_10_64_12);
        ls.add(l10_10_64_13);
        ls.add(l10_10_64_14);
        ls.add(l10_10_64_15);
        ls.add(l10_10_64_16);
        ls.add(l10_10_64_17);
        ls.add(l10_10_64_18);
        ls.add(l10_10_64_27);
        ls.add(l10_10_64_19);
        ls.add(l10_10_64_20);
        ls.add(l10_10_64_21);
        ls.add(l10_10_64_22);
        ls.add(l10_10_64_23);
        ls.add(l10_10_64_24);
        ls.add(l10_10_64_25);
        ls.add(l10_10_64_26);
        ls.add(l10_10_64_28);
        ls.add(l10_10_64_29);
        ls.add(l10_10_64_30);
        ls.add(l10_10_64_31);
        ls.add(l10_10_64_32);
        ls.add(l10_10_64_33);
        ls.add(l10_10_64_34);
        ls.add(l10_10_64_35);
        ls.add(l10_10_64_36);
        ls.add(l10_10_64_37);
        ls.add(l10_10_64_38);
        ls.add(l10_10_64_39);
        ls.add(l10_10_64_40);
        ls.add(l10_10_64_41);
        ls.add(l10_10_64_42);
        ls.add(l10_10_64_43);
        ls.add(l10_10_64_44);
        ls.add(l10_10_64_45);
        ls.add(l10_10_64_46);
        ls.add(l10_10_64_47);
        ls.add(l10_10_64_48);
        ls.add(l10_10_64_49);
        ls.add(l10_10_64_50);
        ls.add(l10_10_64_51);
        ls.add(l10_10_64_52);
        ls.add(l10_10_64_53);
        ls.add(l10_10_64_54);
        ls.add(l10_10_64_55);
        ls.add(l10_10_64_56);
        ls.add(l10_10_64_57);
        ls.add(l10_10_64_58);
        ls.add(l10_10_64_59);
        ls.add(l10_10_64_60);
        ls.add(l10_10_64_61);
        ls.add(l10_10_64_62);
        ls.add(l10_10_64_63);
        ls.add(l10_10_64_64);
        ls.add(l10_10_64_65);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology
     */
    @Test
    public void testFindInterdomainPaths_straight() {
        System.out.println("---------Straight");
        topology_straight = new TopologyTest(ads, ls, ns);

        /*List<Link> pathls = new ArrayList<Link>();
        pathls.add(l20_64_1); pathls.add(l20_64_0); pathls.add(l20_64_2); pathls.add(l10_64_0); pathls.add(l10_64_1);
        Path path1 = new Path();
        path1.setLinks(pathls);*/

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p10_20_32_67);
        rsv.setEndPort(p10_20_32_69);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        List<Link> resLinks = respath1.getLinks();
        System.out.println("Result:"+resLinks.toString());
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#getNeighbours(net.geant.autobahn.network.AdminDomain)}.
     */
    @Ignore
    public void testGetNeighbours() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPathsTest(java.util.List, java.lang.String, java.lang.String, net.geant.autobahn.interdomain.pathfinder.Topology)}.
     */
    @Ignore
    public void testFindInterdomainPathsTest() {
        fail("Not yet implemented");
    }

}
